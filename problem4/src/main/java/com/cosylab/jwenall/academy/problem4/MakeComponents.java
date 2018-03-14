package com.cosylab.jwenall.academy.problem4;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

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

	public MakeComponents(JPanel panel) throws FileNotFoundException {
		device = new NarrowRampedPowerSupplyImpl(new RampedPowerSupplyImpl());
		deviceIcon = createImageIcon("/res/red.png", "Red dot");
		rampIcon = createImageIcon("/res/red.png", "Red dot");
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

		//
		// deviceLabel = makeDeviceLabel();
		// onButton = makeOnButton();
		// offButton = makeOffButton();
		// resetButton = makeResetButton();
		// startButton = makeStartButton();
		// descriptionLabel = makeDescriptionLabel();
		// currentLabel = makeCurrentLabel();
		// setLabel = makeSetLabel();
		// setField = makeSetField();
		// rampLabel = makeRampLabel();
		// rampField = makeRampField();
		// timeLabel = makeTimeLabel();
		// timeField = makeTimeField();
		// startLabel = makeStartLabel();
		// rampStatusLabel = makeRampStatusLabel();
		// logArea = makeLogArea();

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
				device.execute("on", new Object[] {});
				deviceLabel.setIcon(whichIcon("on"));
				// setCurrentArea("on");
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
				device.execute("off", new Object[] {});
				deviceLabel.setIcon(whichIcon("off"));
				// setCurrentArea("off");

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
				device.execute("reset", new Object[] {});
				// setCurrentArea("reset");
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
				device.execute("current_set", new Object[] { Double.parseDouble(setText.getText()) });
				// setCurrentArea("current_set");

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
				for (int i = 0; i < rampValues.length; i++) {
					rampValues[i] = Double.parseDouble(array[i]);
				}
				device.execute("loadRamp", rampValues);
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
				msecs = Integer.parseInt(timeText.getText());
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
		// startButton.addActionListener(new ActionListener() {
		// @Override
		// public void actionPerformed(ActionEvent e) {
		// device.execute("startRamp", new Object[] { msecs });
		// // setCurrentArea("startRamp");
		// rampStatusLabel.setIcon(whichIcon("startRamp"));
		// }
		// });

		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				device.execute("startRamp", new Object[] { msecs });
				Thread currentThread = new Thread() {
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
		logArea = new JTextArea("Log Screen");
		logArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		logArea.setFont(logArea.getFont().deriveFont(24.0f));
		logArea.setBackground(Color.WHITE);
		logArea.setBounds(10, 320, 230, 130);
		return logArea;

	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	private ImageIcon createImageIcon(String path, String description) throws FileNotFoundException {
		ImageIcon temp = new ImageIcon(Toolkit.getDefaultToolkit().getClass().getResource(path));
		return temp;
	}

	private ImageIcon whichIcon(String command) {
		ImageIcon red = new ImageIcon(Toolkit.getDefaultToolkit().getClass().getResource("/res/red.png"));
		ImageIcon green = new ImageIcon(Toolkit.getDefaultToolkit().getClass().getResource("/res/green.png"));
		if (command.equals("on") || command.equals("startRamp")) {
			return green;
		}
		return red;
	}

	// Called from non-UI thread
	private void runCurrent() {
		(new CurrentValueFinder()).execute();
	}

	private class CurrentValueFinder extends SwingWorker<Void, Void> {
		@Override
		protected Void doInBackground() throws Exception {
			for (int i = 0; i <= array.length; i++) {
				currentLabel.setText(device.execute("current_get", new Object[] {}).toString());
				Thread.sleep(msecs);
			}

			return null;
		}
	};

}
