
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.SwingWorker;

public class WorkerRunnable implements Runnable {

	protected Socket clientSocket = null;
	protected String serverText = null;

	private DeviceNarrow device;

	private Object[] changes;
	private Object[] rampValues;

	private String logUpdate = null;
	private boolean finishedRamping = false;
	private boolean isConnected = false;

	private int msecs;

	private Command lastCommand = null;

	private ObjectOutputStream objectOut;

	private String currentValue;
	private boolean isRamping;

	public WorkerRunnable(Socket clientSocket, String serverText) {
		this.clientSocket = clientSocket;
		this.serverText = serverText;
		device = new NarrowRampedPowerSupplyImpl(new RampedPowerSupplyImpl());
		isConnected = true;
	}

	public void run() {
		try {
			System.out.println("In readCommand() method");
			ObjectInputStream objectIn = new ObjectInputStream(clientSocket.getInputStream());
			objectOut = new ObjectOutputStream(clientSocket.getOutputStream());

			while (isConnected) {
				Command inputCommand = (Command) objectIn.readObject();
				System.out.println("Command received is " + inputCommand.getName());
				// sets Object[] to different values
				processInput(inputCommand);
				if (inputCommand.getName().equals("disconnect")) {
					break;
				}
			}

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void processInput(Command input) throws IOException {
		System.out.println(input.getName());
		changes = new Object[5];

		if (input.getName().equals("disconnect")) {
			System.out.println("Client is disconnected from server");
			isConnected = false;

		} else {
			try {
				System.out.println("Executing command " + input.getName());
				device.execute(input.getName(), input.getParamters());
				logUpdate = "Command " + input.getName() + " executed";
			} catch (Exception e) {
				logUpdate = e.getMessage();
				e.printStackTrace();
			}
			// logUpdate = "Command " + input.getName() + " executed";
			if (input.getName().equals("loadRamp")) {
				rampValues = input.getParamters();
			}
			if (input.getName().equals("startRamp")) {
				isRamping = true;
				msecs = Integer.parseInt(input.getParamters()[0]);

				CurrentValueFinder cvf = new CurrentValueFinder();
				cvf.execute();

			} else {
				System.out.println("Setting changes - state: not ramping");
				changes[0] = getCurrentValue();
				changes[1] = logUpdate;
				changes[2] = device.isOn();
				changes[3] = device.isRamping();
				changes[4] = isConnected;
				objectOut.writeObject(changes);
				System.out.println("Objects sent to client in readcommnd");
			}

		}
	}

	private class CurrentValueFinder extends SwingWorker<Void, Void> {
		@Override
		protected Void doInBackground() throws Exception {
			synchronized (this) {
				System.out.println("Do in background");
				Object[] rampingChanges = new Object[5];
				for (int i = 0; i <= rampValues.length; i++) {

					logUpdate = null;
					if (i == rampValues.length) {
						System.out.println("Reached end of rampvalues array");
						isRamping = false;
						logUpdate = "Ramping completed \n";
					}
					System.out.println(
							"Value of current doInbackground: " + device.execute("current_get", null).toString());
					rampingChanges[0] = device.execute("current_get", null).toString();
					rampingChanges[1] = logUpdate;
					rampingChanges[2] = device.isOn();
					rampingChanges[3] = isRamping;
					rampingChanges[4] = true;
					objectOut.writeObject(rampingChanges);
					Thread.sleep(msecs);
				}
				return null;
			}
		}
	};

	private String getCurrentValue() {
		String value;
		if (!device.isOn()) {
			value = "";
			// } else if (device.isRamping()) {
			// value = currentValue;
		} else {
			value = device.execute("current_get", null).toString();
		}
		return value;
	}
}
