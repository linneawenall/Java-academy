package com.cosylab.jwenall.academy.problem9;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientServerMediator extends Thread {

	private static final int PORT = 4444;

	private NarrowRampedPowerSupplyImpl rpsNarrow;

	private Server server;

	private boolean canConnect;

	// Socket
	public Socket clientSocket;
	private ObjectInputStream input = null;
	private ObjectOutputStream output = null;

	public ClientServerMediator(Server server, Socket clientSocket, boolean canConnect) {
		System.out.println("ClientServerMediator initialized");
		this.server = server;
		this.clientSocket = clientSocket;
		this.canConnect = canConnect;
		this.rpsNarrow = server.getNarrowRampedPowerSupply();
		server.addClient(this);
		start();
	}

	@Override
	public void run() {

		try {
			input = new ObjectInputStream(clientSocket.getInputStream());
			output = new ObjectOutputStream(clientSocket.getOutputStream());
		} catch (IOException ioException) {
			System.out.println(ioException.getMessage());
		}

		// Listen for client's requests.
		while (true) {
			System.out.println("ClientServerMediator: listening for clients requests");
			// try {
			System.out.println("ClientServerMediator: In run() while loop");
			// If server is BUSY, inform client about it and disconnect it.
			// if (!canConnect) {
			// output.writeObject(new Command("logUpdate", new Object[] { "Server is BUSY"
			// }));
			// output.writeObject(new Command("disconnect", new Object[] {}));
			// server.disconnectClient(this);
			// System.out.println("ClientServerMediator: disconnects client because cannot
			// connect");
			// return;
			// }

			// Power supply maybe is controlled by another client.
			updatePanel();

			// Get client's request.

			Command command;
			try {
				command = (Command) input.readObject();
				// Execute command
				executeCommand(command.getName(), command.getParamters());
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Execute command
			// executeCommand(command.getName(), command.getParamters());

			// } catch (Exception e) {
			// server.disconnectClient(this);
			// System.out.println("ClientServerMediator: Exception was thrown");
			// }
		}
	}

	private void executeCommand(String commandName, Object[] params) {
		// Try to execute client's request.
		try {
			System.out.println("ClientServerMediator : executes command " + commandName);
			rpsNarrow.execute(commandName, params);
		} catch (Exception e) {
			// Catch all kind of exceptions and inform the client about it.
			server.logUpdate(e.getMessage());
		}
	}

	private void updatePanel() {
		RampedPowerSupplyImpl rps = server.getRampedPowerSupply();
		boolean isOn = rps.isOn();

		// Update power state
		server.onState(isOn);

		// Update ramping state
		server.rampingState(rps.isRamping());

		// Update current value state
		if (isOn)
			server.updateValue(rps.get());
	}

	public Socket getClientSocket() {
		return clientSocket;
	}

	public ObjectOutputStream getOutputStream() {
		return output;
	}
}

// public void startMediator() {
// System.out.println("Mediator started");
// try {
// // Creates a new objectInputStream based on what the Client Socket is sending
// in
// // (inputstream to read data)
//
// inputStream = new ObjectInputStream(clientSocket.getInputStream());
// outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
// if(clientSocket.isClosed()) {
// System.out.println("ClientSocket already closed in startMediator");
// return;
// }
// } catch (IOException e1) {
// e1.printStackTrace();
// }
//
// while (true) {
// if (!canConnect) {
// // outputStream.writeObject(new Command"Something that updates logArea");
//
// System.out.println("Cannot connect in Mediator");
//
// } else {
// commandHandler();
// }
// }
// }
//
// public void commandHandler() {
//
// // Reads the object sent in
// Command received;
// try {
// // Collects the object being sent
// received = (Command) inputStream.readObject();
// String commandName = received.getName();
// System.out.println(commandName);
// Object[] params = received.getParamters();
// System.out.println("Command with request: " +commandName+ "and parameters: "
// +params);
// try {
// server.getDevice().execute(commandName, params);
// } catch (Exception e) {
// System.out.println("Unknown command");
// // panel.logUpdate("Unknown command");
// }
// } catch (Exception e) {
// }
// PrintWriter currentValueOutput;
// try {
// // Creates a printWriter(for text data) on the ClientSockets Outputstream
// (for
// // binary data)
// if(clientSocket.isClosed()) {
// System.out.println("ClientSocket already closed");
// return;
// }
// currentValueOutput = new PrintWriter(clientSocket.getOutputStream(), true);
//
// // Sends this info to the client. This is read by bufferedReader
// currentValueOutput.println(server.getDevice().execute("current_get", null));
// try {
// inputStream.close();
// } catch (IOException e) {
// currentValueOutput.close();
// }
// } catch (IOException e) {
// e.printStackTrace();
// }
//
// }
//
// public void closeConnection() {
// try {
// clientSocket.close();
// } catch (IOException e) {
// e.printStackTrace();
// }
// nbrClients = 0;
//
// System.out.println("Client disconnected from server.");
// }
//
// public Socket getClientSocket() {
// return clientSocket;
// }
//
// public void updatePanel() {
// }
// }