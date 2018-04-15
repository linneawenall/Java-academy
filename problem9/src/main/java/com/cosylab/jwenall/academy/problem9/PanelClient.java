package com.cosylab.jwenall.academy.problem9;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class PanelClient implements Redirecter {
	private DeviceNarrow device;
	private int msecs;
	private JLabel currentLabel, deviceLabel, descriptionLabel, setLabel, rampLabel, timeLabel, startLabel,
			rampStatusLabel;
	private ImageIcon deviceIcon;
	private ImageIcon rampIcon;
	private JTextArea logArea;
	private String[] array;
	private JButton onButton, offButton, resetButton, startButton;
	private JTextField timeText, setText, rampText;
	private CurrentValueFinder cvf;
	private ClientRequests requests;
	public static final int PORT = 4444;

	public static void main(String[] args) throws FileNotFoundException {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("PowerSupply Panel");
				frame.setSize(265, 497);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				JPanel panel = new JPanel();
				panel.setBackground(Color.decode("#CCCCFF"));
				frame.add(panel);
				PanelClient client;
				try {
					client = new PanelClient();
					ClientRequests rd = new ClientRequests(client);

					client.addClientRequests(rd);

					client.placeComponents(panel);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				frame.setVisible(true);
			}
		});
	}

	public PanelClient() throws FileNotFoundException {
		device = new NarrowRampedPowerSupplyImpl(new RampedPowerSupplyImpl());
		deviceIcon = createImageIcon("/red.png", "Red dot");
		rampIcon = createImageIcon("/red.png", "Red dot");

	}

	public void addClientRequests(ClientRequests rd) {
		this.requests = rd;
		requests.connectToSocket("localhost", PORT);
	}

	public void placeComponents(JPanel panel) throws FileNotFoundException {

		panel.setLayout(null);
		panel.add(makeDeviceLabel());
		panel.add(makeOnButton());
		panel.add(makeOffButton());
		panel.add(makeResetButton());
		panel.add(makeStartButton());
		panel.add(makeDescriptionLabel());
		panel.add(makeCurrentLabel());
		panel.add(makeSetLabel());
		panel.add(makeSetField());
		panel.add(makeRampLabel());
		panel.add(makeRampField());
		panel.add(makeTimeLabel());
		panel.add(makeTimeField());
		panel.add(makeStartLabel());
		panel.add(makeRampStatusLabel());
		panel.add(makeLogArea());

	}

	public JLabel makeDeviceLabel() {
		deviceLabel = new JLabel("Device On/Off", deviceIcon, JLabel.LEFT);
		deviceLabel.setFont(deviceLabel.getFont().deriveFont(17.0f));
		deviceLabel.setBounds(10, 10, 170, 25);
		return deviceLabel;
	}

	public JButton makeOnButton() {
		onButton = new JButton("On");
		onButton.setBackground(Color.decode("#999999"));
		onButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		onButton.setFont(onButton.getFont().deriveFont(17.0f));
		onButton.setBounds(10, 50, 60, 25);
		onButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("On button pressed");
				requests.sendCommand(new Command("on", null));
				
				// if(isConnected) {
				// try {
				// outputStream.writeObject(new Command("on", null));
				// outputStream.flush();
				// BufferedReader response = new BufferedReader(new
				// InputStreamReader(clientSocket.getInputStream()));
				// // Reads what the BufferedReader got
				// currentLabel.setText(response.readLine());
				//
				// } catch (IOException e1) {
				// logArea.append("Could not start");
				// e1.printStackTrace();
				// }
				// }
				// try {
				// device.execute("on", new Object[] {});
				// deviceLabel.setIcon(whichIcon("on"));
				// currentLabel.setText(device.execute("current_get", new Object[]
				// {}).toString());
				// } catch (IllegalStateException e3) {
				// logArea.append("Error: Device is already on. \n");
				// }

			}

		});
		return onButton;
	}

	public JButton makeOffButton() {
		offButton = new JButton("Off");
		offButton.setBackground(Color.decode("#999999"));
		offButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		offButton.setFont(offButton.getFont().deriveFont(17.0f));
		offButton.setBounds(89, 50, 60, 25);
		offButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				requests.sendCommand(new Command("off", null));
				// try {
				//
				// device.execute("off", new Object[] {});
				// deviceLabel.setIcon(whichIcon("off"));
				// currentLabel.setText("");
				// } catch (IllegalStateException e2) {
				// logArea.append("Error: Device is already turned off.\n");
				// }

			}

		});
		return offButton;
	}

	public JButton makeResetButton() {
		resetButton = new JButton("Reset");
		resetButton.setBackground(Color.decode("#999999"));
		resetButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		resetButton.setFont(resetButton.getFont().deriveFont(17.0f));
		resetButton.setBounds(166, 50, 60, 25);
		resetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				requests.sendCommand(new Command("reset", null));
				// try {
				// // ClientCommandHandler.sendCommand(new Command("reset",new Object[] {});
				// // Sends to server
				// outputStream.writeObject(new Command("reset", new Object[] {}));
				// outputStream.flush();
				// // device.execute("reset", new Object[] {});
				// // The setText "String" should come from the server
				// // Here we read the details from server
				// BufferedReader response = new BufferedReader(new
				// InputStreamReader(clientSocket.getInputStream()));
				// // Reads what the BufferedReader got
				// currentLabel.setText(response.readLine());
				//
				// // currentLabel.setText(device.execute("current_get", new Object[]
				// // {}).toString());
				// // The logArea text should come from the server. FIX THIS
				// // logArea.append("Current reset to: " + currentLabel.getText() + "\n");
				// } catch (IllegalStateException e1) {
				// logArea.append("Error: Device is turned off, cant reset value.\n");
				// } catch (IOException e1) {
				// // TODO Auto-generated catch block
				// e1.printStackTrace();
				// }
			}

		});
		return resetButton;

	}

	public JLabel makeDescriptionLabel() {
		descriptionLabel = new JLabel("Current Value");
		descriptionLabel.setBounds(140, 100, 80, 25);
		return descriptionLabel;
	}

	public JLabel makeCurrentLabel() {
		currentLabel = new JLabel("");
		currentLabel.setOpaque(true);
		currentLabel.setBackground(Color.decode("#CCCCCC"));
		currentLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		currentLabel.setBounds(10, 100, 120, 25);
		return currentLabel;

	}

	public JLabel makeSetLabel() {
		setLabel = new JLabel("Value to be set");
		setLabel.setBounds(140, 140, 150, 25);
		return setLabel;

	}

	public JTextField makeSetField() {
		setText = new JTextField(20);
		setText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setText.setBounds(10, 140, 120, 25);
		setText.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (isDouble(setText.getText())) {
					requests.sendCommand(
							new Command("current_set", new Object[] { Double.parseDouble(setText.getText()) }));
					// try {
					// outputStream.writeObject(
					// new Command("current_set", new Object[] {
					// Double.parseDouble(setText.getText()) }));
					//
					// outputStream.flush();
					// BufferedReader response = new BufferedReader(
					// new InputStreamReader(clientSocket.getInputStream()));
					// // Reads what the BufferedReader got
					// currentLabel.setText(response.readLine());
					// // device.execute("current_set", new Object[] {
					// // Double.parseDouble(setText.getText()) });
					// // currentLabel.setText(device.execute("current_get", new Object[]
					// // {}).toString());
					// logArea.append("Current set to: " + response.readLine() + "\n");
					// } catch (IllegalStateException e1) {
					// logArea.append("Error: Device is turned off, can't set value.\n");
					// } catch (NumberFormatException e1) {
					// // TODO Auto-generated catch block
					// e1.printStackTrace();
					// } catch (IOException e1) {
					// // TODO Auto-generated catch block
					// e1.printStackTrace();
					// }
					// } else {
					// logArea.append("Error: Only numbers accepted \n");
				} else {
					logUpdate("ERROR - Invalid setting value.");
					//
				}
			}

		});
		return setText;

	}

	public JLabel makeRampLabel() {
		rampLabel = new JLabel("Ramp values");
		rampLabel.setBounds(140, 180, 150, 25);
		return rampLabel;
	}

	public JTextField makeRampField() {
		rampText = new JTextField(20);
		rampText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		rampText.setBounds(10, 180, 120, 25);
		rampText.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				array = rampText.getText().split("[,\\s]+");
				Object[] rampValues = new Object[array.length];
				if (isAllDouble(array)) {
					for (int i = 0; i < rampValues.length; i++) {
						rampValues[i] = Double.parseDouble(array[i]);
					}
					try {
						requests.sendCommand(new Command("loadramp", rampValues));
						// device.execute("loadRamp", rampValues);
					} catch (IllegalStateException e2) {
						logUpdate("Error: Only possible to load ramp when ON.");
					}
				} else {
					logUpdate("Error: Only numbers can be put in\n");
					// logArea.append("Error: Only numbers can be put in\n");
				}
				// if (isAllDouble(array)) {
				// logArea.append("Ramping values loaded: " + Arrays.toString(rampValues) +
				// "\n");
				// }
			}
		});
		return rampText;

	}

	public JLabel makeTimeLabel() {
		timeLabel = new JLabel("Time in msecs");
		timeLabel.setBounds(140, 220, 150, 25);
		return timeLabel;

	}

	public JTextField makeTimeField() {
		timeText = new JTextField(20);
		timeText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		timeText.setBounds(10, 220, 120, 25);
		timeText.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (isInteger(timeText.getText())) {
					msecs = Integer.parseInt(timeText.getText());
					logUpdate("Ramping time set to: " + msecs + " msecs \n");
					// logArea.append("Ramping time set to: " + msecs + " msecs \n");
				} else {
					logUpdate("Error: Only one number accepted \n");
				}
			}
		});
		return timeText;
	}

	public JLabel makeStartLabel() {
		startLabel = new JLabel("Start Ramping");
		startLabel.setBounds(140, 260, 120, 25);
		return startLabel;

	}

	public JLabel makeRampStatusLabel() {
		rampStatusLabel = new JLabel("Ramping On/Off", rampIcon, JLabel.LEFT);
		rampStatusLabel.setFont(rampStatusLabel.getFont().deriveFont(17.0f));
		rampStatusLabel.setBounds(10, 290, 170, 25);
		return rampStatusLabel;

	}

	public JButton makeStartButton() {
		startButton = new JButton("");
		startButton.setBackground(Color.decode("#999999"));
		startButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		startButton.setBounds(10, 260, 120, 25);

		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				requests.sendCommand(new Command("startRamp", new Object[] { msecs }));
				// device.execute("startRamp", new Object[] { msecs });
				// logArea.append("Ramping started \n");
				logUpdate("Ramping started");
				// Probably don't do this
				runCurrent();
			}
		});
		return startButton;

	}

	public JTextArea makeLogArea() {
		logArea = new JTextArea("");
		logArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		logArea.setBackground(Color.WHITE);
		logArea.setBounds(10, 320, 230, 130);
		return logArea;

	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	private void runCurrent() {
		rampStatusLabel.setIcon(whichIcon(true));
		cvf = new CurrentValueFinder();
		cvf.execute();
	}

	private class CurrentValueFinder extends SwingWorker<Void, Void> {
		@Override
		protected Void doInBackground() throws Exception {
			for (int i = 0; i <= array.length; i++) {
				// currentLabel.setText(device.execute("current_get", new Object[]
				// {}).toString());
				// Look over this
				Double[] doubleArray = new Double[1];
				doubleArray[0] = Double.parseDouble(array[i]);
				new Command("updateValue", doubleArray);
				Thread.sleep(msecs);
				if (i == array.length - 1) {
					rampStatusLabel.setIcon(whichIcon(false));
					logUpdate("Ramping completed \n");
				}
			}
			return null;
		}
	};

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

	public DeviceNarrow getDevice() {
		return device;
	}

	private ImageIcon whichIcon(boolean state) {
		ImageIcon red = createImageIcon("/red.png", "Red dot");
		ImageIcon green = createImageIcon("/green.png", "Green dot");
		// if (command.equals("on") || command.equals("startRamp")) {
		if (state) {
			return green;
		}
		return red;
	}

	@Override
	public void onState(boolean state) {
		deviceLabel.setIcon(whichIcon(state));
		// deviceIcon = whichIcon(state);
		// onOffPanel.setState(state);
		if (!state) {
			currentLabel.setText(null);
		}
	}

	@Override
	public void rampingState(boolean state) {
		// rampingPanel.setState(state);
		// rampIcon = whichIcon(state);
		rampStatusLabel.setIcon(whichIcon(state));
		if (!state) {
			resetFrame();
		}
	}

	@Override
	public void updateValue(double value) {
		System.out.println("PanelClient: updateValue to value: " +value);
		currentLabel.setText(Double.toString(value));
		setText.setText(null);
	}

	@Override
	public void logUpdate(String message) {
		// logArea.append(message);
	}

	private void resetFrame() {
		setText.setText(null);
		rampText.setText(null);
		timeText.setText(null);
	}

}
