
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.SwingWorker;

public class WorkerRunnable implements Runnable {

	protected Socket clientSocket = null;
	protected String serverText = null;

	private Command inputCommand;

	private DeviceNarrow device;

	private Object[] changes;
	private Object[] rampValues;

	String currentValue = null;
	String logUpdate = null;
	Boolean isOn = null;
	Boolean isRamping = null;

	private CurrentValueFinder cvf;
	private int msecs;

	private Command lastCommand = null;

	public WorkerRunnable(Socket clientSocket, String serverText) {
		System.out.println("WorkerRunnable initiated");
		this.clientSocket = clientSocket;
		this.serverText = serverText;
		device = new NarrowRampedPowerSupplyImpl(new RampedPowerSupplyImpl());
	}

	public void run() {
		System.out.println("WorkerRunnable run() method");
		readCommand();
	}

	public void readCommand() {
		System.out.println("WorkerRunnable readCommand() method");
		try {
			ObjectInputStream objectIn = new ObjectInputStream(clientSocket.getInputStream());

			changes = processInput(new Command("started", null));

			ObjectOutputStream objectOut = new ObjectOutputStream(clientSocket.getOutputStream());
			objectOut.writeObject(changes);

			cvf = new CurrentValueFinder();

			while ((inputCommand = (Command) objectIn.readObject()) != null) {
				// if (inputCommand!=lastCommand) {
				System.out.println("In if loop with inputCommand: " + inputCommand.getName());
				changes = processInput(inputCommand);
				// lastCommand = inputCommand;
				objectOut.writeObject(changes);
				// }

			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Object[] processInput(Command input) {
		changes = new Object[4];
		if (input.equals(lastCommand)) {


			changes[0] = currentValue;
			changes[1] = null;
			changes[2] = isOn;
			changes[3] = isRamping;

			lastCommand = input;
			return changes;

		} else if (input.getName().equals("started")) {
			System.out.println("Starting server in processInput");
			currentValue = "";
			logUpdate = "Connection established with server";
			isOn = false;
			isRamping = false;
		} else if (input.getName().equals("on")) {
			if (!device.isOn()) {
				device.execute("on", input.getParamters());
				currentValue = device.execute("current_get", null).toString();
				logUpdate = "Device is ON";
				isOn = device.isOn();
				isRamping = false;
			} else {
				currentValue = device.execute("current_get", input.getParamters()).toString();
				logUpdate = "Error: Device is already turned ON, request ignored ";
				isOn = device.isOn();
				isRamping = false;
			}
		} else if (input.getName().equals("off")) {
			if (device.isOn()) {
				device.execute("off", input.getParamters());
				currentValue = "";
				logUpdate = "Device is turned OFF";
				isOn = device.isOn();
				isRamping = false;
			} else {
				currentValue = "";
				logUpdate = "";
				isOn = false;
				isRamping = false;
			}
		} else if (input.getName().equals("reset")) {
			if (device.isOn()) {
				rampValues = input.getParamters();
				device.execute("reset", input.getParamters());
				currentValue = device.execute("current_get", input.getParamters()).toString();
				logUpdate = "Current was reset";
				isOn = device.isOn();
				isRamping = false;
			} else {
				currentValue = device.execute("current_get", input.getParamters()).toString();
				logUpdate = "Could not reset, power is OFF";
				isOn = device.isOn();
				isRamping = false;
			}
		} else if (input.getName().equals("current_set")) {
			if (device.isOn()) {
				device.execute("current_set", input.getParamters());
				currentValue = device.execute("current_get", input.getParamters()).toString();
				String value = (String) input.getParamters()[0].toString();
				logUpdate = "Current was set to " + value;
				isOn = device.isOn();
				isRamping = false;
			} else {
				currentValue = device.execute("current_get", input.getParamters()).toString();
				logUpdate = "Could not set, power is OFF";
				isOn = device.isOn();
				isRamping = false;
			}
		} else if (input.getName().equals("loadRamp")) {
			if (device.isOn()) {
				rampValues = input.getParamters();
				device.execute("loadRamp", input.getParamters());
				currentValue = device.execute("current_get", input.getParamters()).toString();
				String value = (String) input.getParamters()[0].toString();
				logUpdate = "Current was set to " + value;
				isOn = device.isOn();
				isRamping = false;
			} else {
				currentValue = device.execute("current_get", input.getParamters()).toString();
				logUpdate = "Could not set, power is OFF";
				isOn = device.isOn();
				isRamping = false;
			}
		} else if (input.getName().equals("startRamp")) {
			if ((device.isOn() & !device.isRamping())) {
				msecs = (int) input.getParamters()[0];
				System.out.println("In first if statement for startramp");
				device.execute("startRamp", new Object[] { msecs });

				logUpdate = "Current ramping";
				isOn = true;

				cvf = new CurrentValueFinder();
				cvf.execute();

			}
		}

		changes[0] = currentValue;
		changes[1] = logUpdate;
		changes[2] = isOn;
		changes[3] = isRamping;

		lastCommand = input;
		return changes;
	}

	private class CurrentValueFinder extends SwingWorker<Void, Void> {
		@Override
		protected Void doInBackground() throws Exception {
			for (int i = 0; i <= rampValues.length; i++) {
				currentValue = device.execute("current_get", new Object[] {}).toString();
				isRamping = true;
				Thread.sleep(msecs);
				if (i == rampValues.length - 1) {
					isRamping = false;
					logUpdate = "Ramping completed \n";
				}
			}
			return null;
		}
	};
}