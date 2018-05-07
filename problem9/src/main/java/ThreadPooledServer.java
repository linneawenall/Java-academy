
import java.net.ServerSocket;
import java.net.Socket;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPooledServer implements Runnable {

	protected int serverPort = 4444;
	protected ServerSocket serverSocket = null;
	protected boolean isStopped = false;
	protected Thread runningThread = null;
	protected ExecutorService threadPool = Executors.newFixedThreadPool(10);
	protected int nbrClients;
	public ArrayList<WorkerRunnable> clientList;

	public ThreadPooledServer(int port) {
		this.serverPort = port;
		clientList = new ArrayList<WorkerRunnable>();
	}

	public static void main(String[] args) throws FileNotFoundException {
		ThreadPooledServer server = new ThreadPooledServer(4444);
		Thread serverThread = new Thread(server);
		serverThread.start();
		try {
			serverThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		System.out.println("ThreadPooledServer: Server running");
		synchronized (this) {
			this.runningThread = Thread.currentThread();
		}
		openServerSocket();
		while (!isStopped()) {
			Socket clientSocket = null;
			try {
				System.out.println("ThreadPooledServer: waiting for clientSocket");

				clientSocket = this.serverSocket.accept();
			} catch (IOException e) {
				if (isStopped()) {
					System.out.println("Server Stopped.");
					break;
				}
				throw new RuntimeException("ThreadPooledServer: Error accepting client connection", e);
			}

			boolean canConnect = (clientList.size() < 1 ? true : false);
			if (canConnect) {
				WorkerRunnable runnable = new WorkerRunnable(this, clientSocket);
				this.threadPool.execute(runnable);

			} else {
				try {
					ObjectOutputStream objectOut = new ObjectOutputStream(clientSocket.getOutputStream());
					objectOut.writeObject(new Object[] { "", "Server is BUSY, can't connect", false, false, false });
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		this.threadPool.shutdown();

	}

	private synchronized boolean isStopped() {
		return this.isStopped;
	}

	private void openServerSocket() {
		try {
			this.serverSocket = new ServerSocket(this.serverPort);
		} catch (IOException e) {
			throw new RuntimeException("ThreadPooledServer: Cannot open port 4444", e);
		}
	}

	public void addClient(WorkerRunnable client) {
		clientList.add(client);
	}

	public void disconnectClient(WorkerRunnable client) {
		clientList.remove(client);

	}
}
