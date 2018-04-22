
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
			ObjectInputStream objectIn = new ObjectInputStream(clientSocket.getInputStream());

			changes = processInput(new Command("started", null));

			ObjectOutputStream objectOut = new ObjectOutputStream(clientSocket.getOutputStream());
			objectOut.writeObject(changes);
			
			Command inputCommand = null;

			while ((inputCommand = (Command) objectIn.readObject()) != null) {

				changes = processInput(inputCommand);
				objectOut.writeObject(changes);
				if (inputCommand.getName().equals("disconnect")) {
					break;
				}

			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Object[] processInput(Command input) {
		changes = new Object[5];

		// Checks if the while loop sends the same command again
		if (input.equals(lastCommand)) {
			if (!finishedRamping) {
				logUpdate = null;
			} else {
				finishedRamping = false;
			}

			// Set's up initial while loop connection
		} else if (input.getName().equals("started")) {
			if (!isConnected) {
				logUpdate = "Connection established with server";
			} else {
				logUpdate = null;
			}
			isConnected = true;

			// Handles commands when the device is off
		} else if (!device.isOn()) {
			if (input.getName().equals("on")) {
				device.execute(input.getName(), input.getParamters());
				logUpdate = "Device is turned ON";
			} else {
				logUpdate = "Error: Could not perform command,\n power is OFF";
			}
			// Updates log area if commands are made while ramping
		} else if (device.isRamping()) {
			logUpdate = "Error: Could not perform command, \n device is ramping";

			// Handles commands when the device is on
		} else if (device.isOn() && !device.isRamping()) {
			if (input.getName().equals("on")) {
				logUpdate = "Error: Device is already ON";
			} else if (input.getName().equals("setTime")) {
				if (isInteger((String) input.getParamters()[0])) {
					msecs = Integer.parseInt((String) input.getParamters()[0]);
					logUpdate = "Ramping time set to: " + msecs + " msecs";
				} else {
					logUpdate = "Error: Only one number accepted";
				}
			} else if (input.getName().equals("startRamp")) {
				if (!device.isRamping()) {
					if (rampValues == null) {
						logUpdate = "Error: Ramp values have not been loaded";
					} else {
						if (msecs == 0) {
							logUpdate = "Error: No ramping time has been given";
						} else {
							device.execute(input.getName(), new Object[] { msecs });
							logUpdate = "Current ramping";
							CurrentValueFinder cvf = new CurrentValueFinder();
							cvf.execute();
						}
					}
				}
			}
			if (input.getName().equals("off")) {
				logUpdate = "Device is turned OFF";
				device.execute(input.getName(), input.getParamters());

			} else if (input.getName().equals("reset")) {
				rampValues = input.getParamters();
				device.execute(input.getName(), input.getParamters());
				logUpdate = "Current was reset";

			} else if (input.getName().equals("current_set")) {
				if (isDouble(input.getParamters()[0].toString())) {
					device.execute("current_set",
							new Object[] { Double.parseDouble(input.getParamters()[0].toString()) });
					logUpdate = "Current set to: " + getCurrentValue();
				} else {
					logUpdate = "Error: Only numbers accepted";
				}

			} else if (input.getName().equals("loadRamp")) {
				String[] array = input.getParamters()[0].toString().split("[,\\s]+");
				rampValues = new Object[array.length];
				if (isAllDouble(array)) {
					for (int i = 0; i < rampValues.length; i++) {
						rampValues[i] = Double.parseDouble(array[i]);
					}
					device.execute(input.getName(), rampValues);
					logUpdate = "Ramp values loaded";
				} else {
					logUpdate = "Error: Only numbers accepted";
				}

			} else if (input.getName().equals("disconnect")) {
				System.out.println("Client is disconnected from server");
				isConnected = false;
			}
		}
		changes[0] = getCurrentValue();
		changes[1] = logUpdate;
		changes[2] = device.isOn();
		changes[3] = device.isRamping();
		changes[4] = isConnected;

		lastCommand = input;

		return changes;
	}

	private class CurrentValueFinder extends SwingWorker<Void, Void> {
		@Override
		protected Void doInBackground() throws Exception {
			for (int i = 0; i <= rampValues.length; i++) {
				if (i == rampValues.length) {
					finishedRamping = true;
					logUpdate = "Ramping completed";
				}
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

	private boolean isDouble(String number) throws NumberFormatException {
		try {
			Double.parseDouble(number);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	private boolean isAllDouble(String[] array) {
		for (int i = 0; i < array.length; i++) {
			if (!isDouble(array[i])) {
				return false;
			}
		}
		return true;
	}

	private boolean isInteger(String number) throws NumberFormatException {
		try {
			Integer.parseInt(number);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
}