package com.cosylab.jwenall.academy.problem9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientRequests {

	private PanelClient powerSupplyPanel;
	private NarrowRampedPowerSupplyImpl rpsNarrow;

	// Connection
	private Socket clientSocket;
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	private boolean isConnected;
	private int nbrClients;

	public ClientRequests(PanelClient powerSupplyPanel) {
		this.powerSupplyPanel = powerSupplyPanel;
		System.out.println("ClientRequests initiated");
		// isConnected = false;

	}

	public void sendCommand(Command command) {
	//	System.out.println("ClientRequests: sendCommand method - value of isConnected is: " + isConnected);
		// if (isConnected) {
		try {
			System.out.println("ClientRequests: sendCommand(" + command.getName() + ") to server.");
			// Sends the command to the server
			outputStream.writeObject(command);
		} catch (IOException e) {
			powerSupplyPanel.logUpdate(e.getMessage());
			// }
			// } else {
			// System.out.println("ClientRequests: sendCommand method - trying to update
			// logUpdate");
			// powerSupplyPanel.logUpdate("Power supply is disconnected from server.");
		}
	}

	// Connect to server's socket.
	public void connectToSocket(String hostAddress, int port) {
		System.out.println("ClientRequests: Trying to connect");
		// if (!isConnected) {
		if (nbrClients == 0) {
			try {
				clientSocket = new Socket(hostAddress, port);
				outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
				inputStream = new ObjectInputStream(clientSocket.getInputStream());
				isConnected = true;
				nbrClients = 1;
				System.out.println("ClientRequest: connectToSocket - client is connected to the server");
				powerSupplyPanel.logUpdate("Client is connected to the server.");
			} catch (ConnectException | UnknownHostException e) {
				powerSupplyPanel.logUpdate("Cannot connect to the server. Check if the server is running.");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println("ClientRequests: value of isConnected: " + isConnected);
			System.out.println("Number of connected clients: " + nbrClients);
			// Do I need a thread?
			Thread requestListener = new Thread(new Runnable() {
				@Override
				public void run() {
					commandListener();
				}
			});

			// Start listen for server's commands.
			requestListener.start();
		}
		// If its alredy connected.
		else {
			powerSupplyPanel.logUpdate("ClientRequests: Client already connected to the server.");
		}
	}

	public void commandListener() {
		System.out.println("ClientRequests: Listening for commands");
		BufferedReader response;
		try {
			response = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			powerSupplyPanel.updateValue(Double.parseDouble(response.readLine()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// Reads what the BufferedReader got
		// currentLabel.setText(response.readLine());

//		Command command = null;
//		while (true) {
//
//			// try {
//			try {
//				command = (Command) inputStream.readObject();
//			} catch (ClassNotFoundException | IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			Object params = command.getParamters()[0];
//			String commandName = command.getName();
//
//			if (commandName.equals("on")) {
//				System.out.println("ClientRequests: update ON light");
//				powerSupplyPanel.onState((boolean) params);
//
//			} else if (commandName.equals("startramp")) {
//				System.out.println("ClientRequests: update ramping light");
//				powerSupplyPanel.rampingState((boolean) params);
//
//			} else if (commandName.equals("updateValue")) {
//				System.out.println("ClientRequests: updateValue");
//				powerSupplyPanel.updateValue((double) params);
//
//			} else if (commandName.equals("logUpdate")) {
//				System.out.println("ClientRequests: logUpdate");
//				powerSupplyPanel.logUpdate((String) params);
//
//				// } else if (commandName.equals("disconnect")) {
//				// System.out.println("ClientRequests: disconnect");
//				// closeConnection();
//				// return;
//
//			} else
//				powerSupplyPanel.logUpdate("Unknown command");

			// } catch (Exception e) {
			// closeConnection();
			// return;
			// }
//		}
	}

	private void closeConnection() {
		try {
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		isConnected = false;
		nbrClients = 0;

		powerSupplyPanel.logUpdate("Client disconnected from server.");
	}

	public boolean getConnected() {
		return isConnected;
	}

}
