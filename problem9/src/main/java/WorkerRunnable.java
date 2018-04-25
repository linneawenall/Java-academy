
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
	}

	public void run() {
		readCommand();
	}

	public void readCommand() {
		try {
			System.out.println("In readCommand() method");
			ObjectInputStream objectIn = new ObjectInputStream(clientSocket.getInputStream());
			objectOut = new ObjectOutputStream(clientSocket.getOutputStream());
			// changes = processInput(new Command("started", null));
			// Command inputCo = (Command) objectIn.readObject();
			// changes = processInput(inputCo);
			changes = processInput(new Command("started", null));

			// objectOut.writeObject(changes);

			// Command inputCommand = (Command) objectIn.readObject();
			//
			// changes = processInput(inputCommand);
			// objectOut.writeObject(changes);

			while (isConnected) {
				Command inputCommand = (Command) objectIn.readObject();
				System.out.println("Command received is " + inputCommand.getName());
				changes = processInput(inputCommand);
				System.out.println("In while loop" + changes[1]);
				// objectOut.writeObject(changes);
				if (inputCommand.getName().equals("disconnect")) {
					break;
				}
			}

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Object[] processInput(Command input) throws IOException {
		System.out.println(input.getName());
		changes = new Object[5];

		// Checks if the while loop sends the same command again
		// if (input.equals(lastCommand)) {
		// System.out.println("lastcommand was the same");
		// if (!finishedRamping) {
		// logUpdate = null;
		// } else {
		// finishedRamping = false;
		// }

		// Set's up initial while loop connection
		if (input.getName().equals("started")) {
			if (!isConnected) {
				logUpdate = "Connection established with server";
				isConnected = true;
			} else {
				logUpdate = null;
			}
			isConnected = true;

		} else if (input.getName().equals("disconnect")) {
			System.out.println("Client is disconnected from server");
			isConnected = false;

		} else {
			try {
				System.out.println("Executing command " + input.getName());
				device.execute(input.getName(), input.getParamters());
				if (input.getName().equals("startRamp")) {
					isRamping = true;
					msecs = Integer.parseInt(input.getParamters()[0]);
					synchronized (this) {
						CurrentValueFinder cvf = new CurrentValueFinder();

						cvf.execute();
					}
				}
				
				logUpdate = "Command " + input.getName() + " executed";

				if (input.getName().equals("loadRamp")) {
					rampValues = input.getParamters();
				}
			} catch (Exception e) {
				logUpdate = e.getMessage();
				e.printStackTrace();
			}
		}
		System.out.println("logUpdate in readCommand() " + logUpdate);
		if (device.isRamping()) {
			System.out.println("Device is ramping values");
			// changes[0] = device.execute("current_get", null);
			changes[0] = currentValue;
			System.out.println("Currentvalue for device is ramping " + currentValue);
			changes[3] = isRamping;
		} else {
			System.out.println("Not ramping values");
			changes[1] = logUpdate;
			changes[2] = device.isOn();
			// changes[3] = device.isRamping();
			changes[4] = isConnected;
			changes[0] = getCurrentValue();
			changes[3] = device.isRamping();
			objectOut.writeObject(changes);
			System.out.println("Objects sent to client in readcommnd");
		}


		lastCommand = input;



		return changes;
	}

	private class CurrentValueFinder extends SwingWorker<Void, Void> {
		@Override
		protected Void doInBackground() throws Exception {
			System.out.println("Do in background");
			for (int i = 0; i <= rampValues.length; i++) {
				currentValue = device.execute("current_get", null).toString();

				logUpdate = null;
				if (i == rampValues.length - 1) {
					isRamping = false;
					logUpdate = "Ramping completed \n";
				}
				System.out.println(currentValue);
				changes[0] = currentValue;
				changes[1] = logUpdate;
				changes[2] = device.isOn();
				changes[3] = isRamping;
				changes[4] = isConnected;
				objectOut.writeObject(changes);

				System.out.println("Objects from do in background");
				Thread.sleep(msecs);
			}
			return null;
		}
	};

	private String getCurrentValue() {
		String value;
		if (!device.isOn()) {
			value = "";
		} else {
			value = device.execute("current_get", null).toString();
		}
		return value;
	}
}
