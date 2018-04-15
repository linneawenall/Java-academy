package com.cosylab.jwenall.academy.problem9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

//Server contains the main method for the server program and performs the work of listening to the port, 
//establishing connections, and reading from and writing to the socket.

public class Server implements Redirecter {
	private ServerSocket serverSocket;
	private static final int PORT = 4444;
	private boolean isRunning;
	private int nbrClients;

	private ClientServerMediator mediator;
	private Socket clientSocket;
	private static final int MAX_CLIENTS = 1;
	public ArrayList<ClientServerMediator> clientList;

	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;

	private RampedPowerSupplyImpl rampedPowerSupply;
	private NarrowRampedPowerSupplyImpl rpsNarrow;

	// private DeviceNarrow device;

	public static void main(String[] arg) {
		// A serverSocket is started
		Server server = new Server();
		boolean start = true;
		server.runServer(start);

	}

	public Server() {
		try {
			System.out.println("Server: Initilizing server");
			serverSocket = new ServerSocket(PORT);
			nbrClients = 0;
			clientList = new ArrayList<ClientServerMediator>();
		} catch (IOException e) {
			System.out.println("Server: Socket cannot be started at port " + PORT + ".");
		}

		rpsNarrow = new NarrowRampedPowerSupplyImpl(new RampedPowerSupplyImpl());

		System.out.println("Server: Power supply initialized.");
	}

	public void runServer(boolean start) {
		isRunning = start;
		if (start) {
			System.out.println("Server: runServer(" + start + ") method called");
			lookForClient();
		} else {
			// closeServer();
		}
	}

	private void lookForClient() {
		try {
			System.out.println("Server: listening on port " + PORT + "\n");

			while (true) {
				System.out.println("Server: In lookForClient while method");
				// Wait for client to connect to this socket.
				clientSocket = serverSocket.accept();
				System.out.println("Server: Is clientSocket closed? " + clientSocket.isClosed());
				// True, if the nbr of clients are fewer than 1

				// boolean canConnect = nbrClients < MAX_CLIENTS ? true : false;
				// System.out.println("Value of canConnect "+canConnect);
				try {
					// Maybe ClientServerMediator should have ServerSocket param instead of Server
					// mediator = new ClientServerMediator(this, clientSocket, canConnect);

					//SECOND LOOP NEVER REACHES THIS: WHY?
					inputStream = new ObjectInputStream(clientSocket.getInputStream());
					outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
					System.out.println("Server: Streams created successfully");
					if (clientSocket.isClosed()) {
						System.out.println("Server: ClientSocket already closed in startMediator");
						break;
					}
					commandHandler();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}

			// Power off -> makes it stop listening for clients & closes server
			// runServer(false);

		} catch (IOException | NullPointerException e) {
			System.out.println("Server cannot run. \nClosing server");
		}
	}

	private void closeServer() {
		Socket clientSocket = mediator.getClientSocket();
		try {
			clientSocket.close();
			nbrClients = 0;
			System.out.println("Server: ClientSocket closed");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// mediator.closeConnection();
		try {

			serverSocket.close();
			System.out.println("Server: Server is closed");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	public ClientServerMediator getMediator() {
		return mediator;
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void commandHandler() {

		// Reads the object sent in
		Command received;
		try {
			// Collects the object being sent
			received = (Command) inputStream.readObject();
			String commandName = received.getName();
			System.out.println("Server - commandHandler() - Command received was: " + commandName);
			Object[] params = received.getParamters();
			System.out.println("Server: Command with request: " + commandName + " and parameters: " + params);
			try {
				rpsNarrow.execute(commandName, params);
			} catch (Exception e) {
				System.out.println("Server - commandHandler(): Unknown command");
				// panel.logUpdate("Unknown command");
			}
		} catch (Exception e) {
			System.out.println("Server: commandHandler(): Exception thrown");
		}
		PrintWriter currentValueOutput;
		try {
			// Creates a printWriter(for text data) on the ClientSockets Outputstream

			// binary data)
			if (clientSocket.isClosed()) {
				System.out.println("Server: ClientSocket already closed");
				return;
			}
			currentValueOutput = new PrintWriter(clientSocket.getOutputStream(), true);

			// Sends this info to the client. This is read by bufferedReader
			currentValueOutput.println(rpsNarrow.execute("current_get", null));

			try {
				inputStream.close();
			} catch (IOException e) {
				currentValueOutput.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		updatePanel();
	}

	private void updatePanel() {
		System.out.println("Server - updatePanel()");
		RampedPowerSupplyImpl rps = getRampedPowerSupply();
		boolean isOn = rps.isOn();

		// Update power state
		onState(isOn);

		// Update ramping state
		rampingState(rps.isRamping());

		// Update current value state
		if (isOn)
			updateValue(rps.get());
	}

	// import java.io.IOException;
	// import java.net.ServerSocket;
	// import java.net.Socket;
	// import java.util.ArrayList;
	//
	// public class Server implements Redirecter {
	//
	// // Properties
	// private static final int PORT = 4444;
	// private static final int MAX_CLIENTS = 1;
	//
	// private ServerSocket serverSocket;
	// private RampedPowerSupplyImpl rampedPowerSupply;
	// private NarrowRampedPowerSupplyImpl rpsNarrow;
	//
	// private boolean isRunning;
	// public ArrayList<ClientServerMediator> clientList;
	//
	// public static void main(String args[]) {
	//
	// boolean powerOn = true;
	// new Server().setPower(powerOn);
	// }
	//
	// public Server() {
	// initializeServer();
	// initializePowerSupply();
	// }
	//
	// private void initializeServer() {
	// try {
	// System.out.println("Initilizing server");
	// serverSocket = new ServerSocket(PORT);
	// clientList = new ArrayList<ClientServerMediator>();
	// } catch (IOException e) {
	// System.out.println("Socket cannot be started at port " + PORT + ".");
	// }
	// }
	//
	// private void initializePowerSupply() {
	// System.out.println("Initilizing power supply");
	//
	// rampedPowerSupply = new RampedPowerSupplyImpl();
	//
	// // rampedPowerSupply.addRedirecter(this);
	// rpsNarrow = new NarrowRampedPowerSupplyImpl(rampedPowerSupply);
	//
	// System.out.println("Power supply initialized.");
	// }
	//
	// public void setPower(boolean setOn) {
	// isRunning = setOn;
	//
	// // Start listening
	// if (setOn) {
	// System.out.println("Server started!");
	// listenForClients();
	//
	// }
	// // Stop server
	// else {
	// stopServer();
	// }
	// }
	//
	// private void listenForClients() {
	// try {
	// System.out.println("Server is listening on port " + PORT + "\n");
	//
	// while (isRunning()) {
	// // Wait for client to connect to this socket.
	// Socket clientSocket = serverSocket.accept();
	//
	// // True, if the current client can use servers services
	// boolean canConnect = (clientList.size() < MAX_CLIENTS ? true : false);
	//
	// try {
	// new ClientServerMediator(this, clientSocket, canConnect);
	// } catch (Exception e) {
	// System.out.println(e.getMessage());
	// }
	// }
	//
	// // Power off
	// setPower(false);
	//
	// } catch (IOException | NullPointerException e) {
	// System.out.println("Server cannot run. \nClosing...");
	// }
	// }
	//
	// public void disconnectClient(ClientServerMediator client) {
	// if (!clientList.contains(client))
	// return;
	//
	// Socket clientSocket = client.getClientSocket();
	// try {
	// clientSocket.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// } finally {
	// if (clientSocket != null) {
	// try {
	// clientSocket.close();
	// } catch (Exception e) {
	// }
	// }
	// }
	//
	// System.out.println("Client " + (clientList.indexOf(client) + 1) + "
	// disconnected.");
	//
	// // Remove client from client's list.
	// removeClient(client);
	// }
	//
	//
	// private void stopServer() {
	// // Disconnect all clients.
	// for (int i = 0; i < clientList.size(); i++)
	// disconnectClient(clientList.get(i));
	//
	// // Close the socket.
	// try {
	// serverSocket.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// } finally {
	// if (serverSocket != null) {
	// try {
	// serverSocket.close();
	// } catch (IOException e) {
	// }
	// }
	// }
	//
	// System.out.println("Server stopped!");
	// System.out.println("BYE!");
	//
	// // Quit server
	// System.exit(0);
	// }

	public boolean isRunning() {
		return isRunning;
	}

	public RampedPowerSupplyImpl getRampedPowerSupply() {
		return rampedPowerSupply;
	}

	public NarrowRampedPowerSupplyImpl getNarrowRampedPowerSupply() {
		return rpsNarrow;
	}

	public void addClient(ClientServerMediator client) {
		clientList.add(client);
		System.out.println("Server: Client " + (clientList.indexOf(client) + 1) + " connected.");
	}

	public void removeClient(ClientServerMediator client) {
		clientList.remove(client);
	}

	public ArrayList<ClientServerMediator> getClientList() {
		return clientList;
	}

	@Override
	public void onState(boolean state) {
		System.out.println("Server: onState sending command " + state);
		try {
			outputStream.writeObject(new Command("on", new Object[] { state }));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// try {
		// System.out.println("Server: onState sending command " + state);
		// for (ClientServerMediator client : clientList) {
		// client.getOutputStream().writeObject(new Command("on", new Object[] { state
		// }));
		// }
		// } catch (IOException e) {
		// }
	}

	@Override
	public void rampingState(boolean state) {
		System.out.println("Server: rampingState sending command " + state);
		try {
			outputStream.writeObject(new Command("startramp", new Object[] { state }));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// try {
		// System.out.println("Server: rampingState sending command " + state);
		// for (ClientServerMediator client : clientList) {
		// client.getOutputStream().writeObject(new Command("startramp", new Object[] {
		// state }));
		// }
		// } catch (IOException e) {
		// }
	}

	@Override
	public void updateValue(double value) {
		System.out.println("Server: updateValue sending command " + value);
		try {
			outputStream.writeObject(new Command("updateValue", new Object[] { value }));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// try {
		// System.out.println("Server: updateValue sending command " + value);
		// for (ClientServerMediator client : clientList) {
		// client.getOutputStream().writeObject(new Command("updateValue", new Object[]
		// { value }));
		// }
		// } catch (IOException e) {
		// }
	}

	@Override
	public void logUpdate(String text) {
		System.out.println("Server: updateValue sending command " + text);
		try {
			outputStream.writeObject(new Command("logUpdate", new Object[] { text }));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// try {
		// System.out.println("Server: logUpdate sending command " + text);
		// for (ClientServerMediator client : clientList) {
		// client.getOutputStream().writeObject(new Command("logUpdate", new Object[] {
		// text }));
		// }
		// } catch (IOException e) {
		// }
	}
}
