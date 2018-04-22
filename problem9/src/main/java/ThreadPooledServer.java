
import java.net.ServerSocket;
import java.net.Socket;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPooledServer implements Runnable {

	protected int serverPort = 4444;
	protected ServerSocket serverSocket = null;
	protected boolean isStopped = false;
	protected Thread runningThread = null;
	protected ExecutorService threadPool = Executors.newFixedThreadPool(10);
	protected int nbrClients;

	public ThreadPooledServer(int port) {
		this.serverPort = port;
	}

	public static void main(String[] args) throws FileNotFoundException {
		ThreadPooledServer server = new ThreadPooledServer(4444);
		new Thread(server).start();

		try {
			Thread.sleep(20 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("ThreadPooledServer: Stopping Server");
		server.stop();
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
			boolean canConnect = (nbrClients < 1 ? true : false);
			if (canConnect) {
				nbrClients = 1;
				this.threadPool.execute(new WorkerRunnable(clientSocket, "Thread Pooled Server"));
			} else {
				try {

					ObjectOutputStream objectOut = new ObjectOutputStream(clientSocket.getOutputStream());
					objectOut.writeObject(new Object[] { "Server is BUSY, can't connect" });
					System.out.println("New client can't connect");

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		this.threadPool.shutdown();
		System.out.println("ThreadPooledServer run() method: Server Stopped.");
	}

	private synchronized boolean isStopped() {
		return this.isStopped;
	}

	public synchronized void stop() {
		this.isStopped = true;
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			throw new RuntimeException("ThreadPooledServer: Error closing server", e);
		}
	}

	private void openServerSocket() {
		try {
			this.serverSocket = new ServerSocket(this.serverPort);
		} catch (IOException e) {
			throw new RuntimeException("ThreadPooledServer:Cannot open port 4444", e);
		}
	}

}
