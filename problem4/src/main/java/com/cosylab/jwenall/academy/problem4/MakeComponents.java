package com.cosylab.jwenall.academy.problem4;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.IllegalFormatException;

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

public class MakeComponents {
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
	private Thread currentThread;

	public MakeComponents(JPanel panel) throws FileNotFoundException {
		device = new NarrowRampedPowerSupplyImpl(new RampedPowerSupplyImpl());
		deviceIcon = createImageIcon("/red.png", "Red dot");
		rampIcon = createImageIcon("/red.png", "Red dot");
		placeComponents(panel);
	}

	private void placeComponents(JPanel panel) throws FileNotFoundException {

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
				try {
					device.execute("on", new Object[] {});
					deviceLabel.setIcon(whichIcon("on"));
					currentLabel.setText(device.execute("current_get", new Object[] {}).toString());
				} catch (IllegalStateException e3) {
					logArea.append("Error: Device is already on. \n");
				}

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
				try {

					device.execute("off", new Object[] {});
					deviceLabel.setIcon(whichIcon("off"));
					currentLabel.setText("");
				} catch (IllegalStateException e2) {
					logArea.append("Error: Device is already turned off.\n");
				}

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

				try {
					device.execute("reset", new Object[] {});
					currentLabel.setText(device.execute("current_get", new Object[] {}).toString());
					logArea.append("Current reset to: " + currentLabel.getText() + "\n");
				} catch (IllegalStateException e1) {
					logArea.append("Error: Device is turned off, can�t reset value.\n");
				}
			}

		});
		return resetButton;

	}

	public JLabel makeDescriptionLabel() { // for current value
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
					try {
						device.execute("current_set", new Object[] { Double.parseDouble(setText.getText()) });
						currentLabel.setText(device.execute("current_get", new Object[] {}).toString());
						logArea.append(
								"Current set to: " + device.execute("current_get", new Object[] {}).toString() + "\n");
					} catch (IllegalStateException e1) {
						logArea.append("Error: Device is turned off, can�t set value.\n");
					}
				} else {
					logArea.append("Error: Only numbers accepted \n");


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
						device.execute("loadRamp", rampValues);
					} catch (IllegalStateException e2) {
						logArea.append("Error: Only possible to load ramp when ON.");
					}
				} else {
					logArea.append("Error: Only numbers can be put in\n");
				}
				if (isAllDouble(array)) {
					logArea.append("Ramping values loaded: " + Arrays.toString(rampValues) + "\n");
				}
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
						logArea.append("Ramping time set to: " + msecs + " msecs \n"); //should only happen when on though
					
				} else {
					logArea.append("Error: Only one number accepted \n");
				}
				


		}});
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
				device.execute("startRamp", new Object[] { msecs });
				logArea.append("Ramping started \n");
				// REVIEW (medium): I don't think you really need this thread, since you are already using "SwingWorker"
				// for checking current values asynchronously.
				currentThread = new Thread() {
					public void run() {
						runCurrent();

					}
				};
				currentThread.start();
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

	private ImageIcon whichIcon(String command) {
		ImageIcon red = createImageIcon("/red.png", "Red dot");
		ImageIcon green = createImageIcon("/green.png", "Green dot");
		if (command.equals("on") || command.equals("startRamp")) {
			return green;
		}
		return red;
	}

	// Called from non-UI thread
	private void runCurrent() {
		rampStatusLabel.setIcon(whichIcon("startRamp"));
		// REVIEW (high): you should assign the "new CurrentValueFinder()" to some class variable. If not, it
		// can happen that Java's Garbage Collector will remove it once you exit the "runCurrent" method.
		(new CurrentValueFinder()).execute();
	}

	private class CurrentValueFinder extends SwingWorker<Void, Void> {
		@Override
		protected Void doInBackground() throws Exception {
			for (int i = 0; i <= array.length; i++) {
				currentLabel.setText(device.execute("current_get", new Object[] {}).toString());
				if (currentThread.isInterrupted()) {
					rampStatusLabel.setIcon(whichIcon("off"));
				}
				Thread.sleep(msecs);
				if (i == array.length - 1) {
					rampStatusLabel.setIcon(whichIcon("off"));
					logArea.append("Ramping completed \n");
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
	
}
