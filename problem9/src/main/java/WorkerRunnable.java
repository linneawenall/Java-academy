
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;

public class WorkerRunnable implements Runnable {

	// Server
	protected Socket clientSocket;
	// REVIEW (medium): this variable is not needed anywhere.
	protected String serverText;
	private ThreadPooledServer server;
	private boolean canConnect;

	private DeviceNarrow device;

	private Object[] changes;

	private String logUpdate;

	private boolean isConnected;

	public WorkerRunnable(ThreadPooledServer server, boolean canConnect, Socket clientSocket, String serverText) {
		this.clientSocket = clientSocket;
		this.serverText = serverText;
		this.server = server;
		this.canConnect = canConnect;

		device = new NarrowRampedPowerSupplyImpl(new RampedPowerSupplyImpl());
		isConnected = true;
		if (canConnect) {
			server.addClient(this);
		}
	}

	public void run() {
		try {
			ObjectInputStream objectIn = new ObjectInputStream(clientSocket.getInputStream());
			ObjectOutputStream objectOut = new ObjectOutputStream(clientSocket.getOutputStream());

			while (isConnected) {
				Command inputCommand = (Command) objectIn.readObject();
				System.out.println("Command received is " + inputCommand.getName());
				changes = processInput(inputCommand);
				objectOut.writeObject(changes);
				if (inputCommand.getName().equals("disconnect")) {
					server.disconnectClient(this);
					break;
				}
			}

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Object[] processInput(Command input) throws IOException {
		changes = new Object[5];
		logUpdate = null;
		if (input.getName().equals("disconnect")) {
			System.out.println("Client is disconnected from server");
			isConnected = false;
		} else if (input.getName().equals("ramping")) {
			// REVIEW (medium): what is the purpose of this command? Do you use it for checking if the ramping is finished?
			// If so, check the review comment there: there is a better way of checking if the ramping is finished
			// rather than introducing a special command to do that.
			if (!device.isRamping()) {
				logUpdate = "Ramping completed";
			}
		} else if (input.getName().equals("Can client connect?")) {
			// REVIEW (medium): this command is not requested by the assignment. remove it.
			if (!canConnect) {
				logUpdate = "Server is BUSY, can't connect";
				isConnected = false;
			}
		} else {
			try {
				System.out.println("Executing command " + input.getName());
				device.execute(input.getName(), input.getParamters());
				logUpdate = "Command " + input.getName() + " executed";
			} catch (Exception e) {
				logUpdate = e.getMessage();
			}
		}
		changes[0] = getCurrentValue();
		changes[1] = logUpdate;
		changes[2] = device.isOn();
		changes[3] = device.isRamping();
		changes[4] = isConnected;

		return changes;
	}

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
