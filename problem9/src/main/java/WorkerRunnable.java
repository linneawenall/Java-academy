
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;

public class WorkerRunnable implements Runnable {

	// Server
	protected Socket clientSocket;
	private ThreadPooledServer server;

	private DeviceNarrow device;

	private Object[] changes;

	private String logUpdate;

	private boolean isConnected;

	public WorkerRunnable(ThreadPooledServer server, Socket clientSocket) {
		this.clientSocket = clientSocket;
		this.server = server;

		device = new NarrowRampedPowerSupplyImpl(new RampedPowerSupplyImpl());
		isConnected = true;
		server.addClient(this);
	}

	public void run() {
		try {
			ObjectInputStream objectIn = new ObjectInputStream(clientSocket.getInputStream());
			ObjectOutputStream objectOut = new ObjectOutputStream(clientSocket.getOutputStream());
			objectOut.writeObject(changes);

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
