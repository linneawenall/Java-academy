
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

	private String inputLine, outputLine;

	private Command inputCommand;

	private DeviceNarrow device;

	private BufferedReader in;
	private PrintWriter out;

	private Object[] changes;

	String currentValue = null;
	String logUpdate = null;
	Boolean isOn = null;
	Boolean isRamping = null;

	private CurrentValueFinder cvf;

	public WorkerRunnable(Socket clientSocket, String serverText) {
		System.out.println("WorkerRunnable initiated");
		this.clientSocket = clientSocket;
		this.serverText = serverText;
		device = new NarrowRampedPowerSupplyImpl(new RampedPowerSupplyImpl());
	}

	public void run() {
		try {
			System.out.println("WorkerRunnable run() method");
			InputStream input = clientSocket.getInputStream();
			OutputStream output = clientSocket.getOutputStream();

			// Från kkServer
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			// long time = System.currentTimeMillis();
			// output.write(("HTTP/1.1 200 OK\n\nWorkerRunnable: " + this.serverText + " - "
			// + time + "").getBytes());
			// output.close();
			// input.close();
			// System.out.println("WorkerRunnable: Request processed: " + time);
			readCommand();
		} catch (IOException e) {
			// report exception somewhere.
			e.printStackTrace();
		}
	}

	// public void readCommand() {
	// System.out.println("WorkerRunnable readCommand() method");
	// ObjectInputStream objectIn;
	// try {
	// // objectIn = new ObjectInputStream(clientSocket.getInputStream());
	// // // Reads the object sent in
	// // String receivedCommand = (String) objectIn.readObject();
	// // // Creates a printWriter(for text data) on the ClientSockets Outputstream
	// // (for
	// // // binary data)
	// // PrintWriter output = new PrintWriter(clientSocket.getOutputStream(),
	// true);
	// // // Sends this info to the client
	// // output.println(receivedCommand);
	//
	// outputLine = processInput(null);
	// out.println(outputLine);
	//
	// // Ser till att programmet inte avslutas förrän vi når sista raden
	// // I nuläget är inputLine variablen fromUser i PanelClient
	// // LOOK HERE - here I need something that makes sure that commands that are
	// the
	// // same are processed only once, which is why I get an error now
	// while ((inputLine = in.readLine()) != null) {
	// System.out.println("In while loop with inputLine: " + inputLine);
	// outputLine = processInput(inputLine);
	// // outputLine = receivedCommand;
	// out.println(outputLine);
	// if (outputLine.equals("off"))
	// break;
	// }
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// // } catch (ClassNotFoundException e) {
	// // // TODO Auto-generated catch block
	// // e.printStackTrace();
	// }
	// }

	public void readCommand() {
		System.out.println("WorkerRunnable readCommand() method");
		try {
			ObjectInputStream objectIn = new ObjectInputStream(clientSocket.getInputStream());
			// String receivedCommand = (String) objectIn.readObject();
			//
			// System.out.println("Workerrunnable received" +receivedCommand);

			changes = processInput(new Command("started", null));

			ObjectOutputStream objectOut = new ObjectOutputStream(clientSocket.getOutputStream());
			objectOut.writeObject(changes);

			cvf = new CurrentValueFinder();

			while ((inputCommand = (Command) objectIn.readObject()) != null) {
				System.out.println("In while loop with inputCommand: " + inputCommand.getName());
				changes = processInput(inputCommand);
				objectOut.writeObject(changes);
			}
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// } catch (ClassNotFoundException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
		}

	}

	// public String processInput(String input) {
	// if (input == null) {
	// return "Server started";
	// } else if (input.equals("on")) {
	// // // try {
	// // // device.execute("on", new Object[] {});
	// // // deviceLabel.setIcon(whichIcon("on"));
	// // // currentLabel.setText(device.execute("current_get", new Object[]
	// // // {}).toString());
	// // // } catch (IllegalStateException e3) {
	// // // logArea.append("Error: Device is already on. \n");
	// // // }
	// // if (!device.isOn()) {
	// // device.execute("on", null);
	// // } else {
	// // return "On command already processed - ignored";
	// // }
	// if (!device.isOn()) {
	// try {
	// device.execute("on", null);
	// } catch (IllegalStateException e3) {
	// return "On command already processed - ignored";
	// }
	// }
	// return "On command processed";
	// } else if (input.equals("off")) {
	// try {
	//
	// device.execute("off", new Object[] {});
	// // // deviceLabel.setIcon(whichIcon("off"));
	// // // currentLabel.setText("");
	// } catch (IllegalStateException e2) {
	// return "Error: Device is already turned off.\n";
	// }
	// return "Off command processed";
	// }
	// // Otherwise return received command
	// return "Server running";
	// }

	public Object[] processInput(Command input) {
		Object[] rampValues = input.getParamters();

		changes = new Object[4];
		if (input.getName().equals("started")) {
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
			if (device.isOn() && !device.isRamping()) {

				device.execute("startRamp", input.getParamters());

				logUpdate = "Current ramping";

				cvf.setStuff(input, rampValues);
				cvf.execute();

				cvf.setInfo(changes);
				// currentValue = device.execute("current_get",
				// input.getParamters()).toString();

				// isOn = device.isOn();
				// fix this bit
				// isRamping = device.isRamping();
			} else if (device.isRamping()) {
				cvf.setInfo(changes);
			}
		}
		changes[0] = currentValue;
		changes[1] = logUpdate;
		changes[2] = isOn;
		changes[3] = isRamping;
		return changes;
	}

	private class CurrentValueFinder extends SwingWorker<Void, Void> {
		private Command command;
		private Object[] rampArray;

		public void setStuff(Command command, Object[] rampArray) {
			this.command = command;
			this.rampArray = rampArray;
		}

		public Object[] setInfo(Object[] changes) {
			changes[0] = currentValue;
			// changes[1] = logUpdate;
			changes[2] = isOn;
			changes[3] = isRamping;
			return changes;
		}

		@Override
		protected Void doInBackground() throws Exception {
			for (int i = 0; i <= rampArray.length; i++) {
				currentValue = device.execute("current_get", null).toString();
				isRamping = device.isRamping();
				isOn = device.isOn();
				Thread.sleep((long) command.getParamters()[0]);

				if (i == rampArray.length - 1) {
					isRamping = device.isRamping();
					logUpdate = "Ramping completed \n";
				}

			}
			return null;
		}
	};
}