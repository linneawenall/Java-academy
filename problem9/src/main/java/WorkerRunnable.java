
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

	private String currentValue = null;
	private String logUpdate = null;
	private Boolean isOn = null;
	private Boolean isRamping = null;
	private boolean finishedRamping = false;
	private boolean isConnected = false;

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

			while ((inputCommand = (Command) objectIn.readObject()) != null) {
				changes = processInput(inputCommand);
				objectOut.writeObject(changes);

			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Object[] processInput(Command input) {
		changes = new Object[5];

		// Checks if the while loop sends the same command again
		if (input.equals(lastCommand)) {
			if (finishedRamping) {
				logUpdate = "Ramping completed";
				finishedRamping = false;
			} else {
				logUpdate = null;
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
				currentValue = device.execute("current_get", null).toString();
			} else {
				currentValue = "";
				logUpdate = "Could not perform command, power is OFF";
			}
//GET BACK HERE _ MAKE SERVER HANDLE CHECKING IF PARAMETERS ARE NUMBERS ETC
			// Handles commands when the device is on
		} else if (device.isOn()) {
			if (input.getName().equals("on")) {
				logUpdate = "Device is already ON, request ignored";
			} else if (input.getName().equals("setTime")) {
				if (isInteger((String) input.getParamters()[0])) {
					msecs = Integer.parseInt((String) input.getParamters()[0]);
					logUpdate = "Ramping time set to: " + msecs + " msecs \n";
				} else {
					logUpdate = "Error: Only one number accepted \n";
				}
			} else if (input.getName().equals("startRamp")) {
				if (!device.isRamping()) {
					if (rampValues == null) {
						logUpdate = "Ramp values have not been loaded";
					} else {
						msecs = (int) input.getParamters()[0];
						if (msecs == 0) {
							logUpdate = "No ramping time has been given";
						} else {
							device.execute(input.getName(), new Object[] { msecs });
							logUpdate = "Current ramping";
							cvf = new CurrentValueFinder();
							cvf.execute();
						}
					}
				}
			} else {
				device.execute(input.getName(), input.getParamters());
			}
			if (input.getName().equals("off")) {
				logUpdate = "Device is turned OFF \n & disconnected from server";
				isConnected = false;

			} else if (input.getName().equals("reset")) {
				rampValues = input.getParamters();
				logUpdate = "Current was reset";

			} else if (input.getName().equals("current_set")) {
				logUpdate = "Current was set to" + currentValue;

			} else if (input.getName().equals("loadRamp")) {
				rampValues = input.getParamters();
				logUpdate = "Ramp values loaded";
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
				currentValue = device.execute("current_get", new Object[] {}).toString();
				Thread.sleep(msecs);
				if (i == rampValues.length - 1) {
					finishedRamping = true;
				}
			}
			return null;
		}
	};

	private String getCurrentValue() {
		String value;
		if (!device.isOn()) {
			value = "";
		} else if (device.isOn() && device.isRamping()) {
			value = currentValue;
		} else {
			value = device.execute("current_get", null).toString();
		}
		return value;
	}

	// if (isDouble(setText.getText())) {
	// fromUser = new Command("current_set", new Object[] {
	// Double.parseDouble(setText.getText()) });
	// System.out.println("Current setText fired");
	// } else {
	// // Should come from server
	// logArea.append("Only numbers");
	// }
	// } else if (evt.getSource().equals(rampText)) {
	// array = rampText.getText().split("[,\\s]+");
	// Object[] rampValues = new Object[array.length];
	// if (isAllDouble(array)) {
	// for (int i = 0; i < rampValues.length; i++) {
	// rampValues[i] = Double.parseDouble(array[i]);
	// }
	// fromUser = new Command("loadRamp", rampValues);
	// } else {
	// // Should come from Server
	// logArea.append("Only numbers");
	// }
	// } else if (evt.getSource().equals(timeText)) {
	// // These things should be done in WorkerRunnable
	// if (isInteger(timeText.getText())) {
	// msecs = Integer.parseInt(timeText.getText());
	// logArea.append("Ramping time set to: " + msecs + " msecs \n");
	// } else {
	// logArea.append("Error: Only one number accepted \n");
	//
	// }

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