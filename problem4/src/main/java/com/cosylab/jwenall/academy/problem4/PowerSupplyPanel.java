package com.cosylab.jwenall.academy.problem4;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class PowerSupplyPanel extends JFrame {
	private static DeviceNarrow device;
	private static int msecs;

	public static void main(String[] args) {
		device = new NarrowRampedPowerSupplyImpl(new RampedPowerSupplyImpl());
		JFrame frame = new JFrame("PowerSupply Panel");
		frame.setSize(265, 497);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setBackground(Color.decode("#CCCCFF"));
		frame.add(panel);
		placeComponents(panel);

		frame.setVisible(true);
	}

	private static void placeComponents(JPanel panel) {

		panel.setLayout(null);

		JLabel deviceLabel = new JLabel("Device On/Off");
		deviceLabel.setFont(deviceLabel.getFont().deriveFont(17.0f));
		deviceLabel.setBounds(35, 10, 170, 25);
		panel.add(deviceLabel);

		JButton onButton = new JButton("On");
		onButton.setBackground(Color.decode("#999999"));
		onButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		onButton.setFont(onButton.getFont().deriveFont(17.0f));
		onButton.setBounds(10, 50, 60, 25);
		onButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				device.execute("on", new Object[] {});

			}

		});
		panel.add(onButton);

		JButton offButton = new JButton("Off");
		offButton.setBackground(Color.decode("#999999"));
		offButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		offButton.setFont(offButton.getFont().deriveFont(17.0f));
		offButton.setBounds(89, 50, 60, 25);
		offButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				device.execute("off", new Object[] {});

			}

		});
		panel.add(offButton);

		JButton resetButton = new JButton("Reset");
		resetButton.setBackground(Color.decode("#999999"));
		resetButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		resetButton.setFont(resetButton.getFont().deriveFont(17.0f));
		resetButton.setBounds(166, 50, 60, 25);
		resetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				device.execute("reset", new Object[] {});

			}

		});
		panel.add(resetButton);

		JLabel currentLabel = new JLabel("Current Value");
		currentLabel.setBounds(140, 100, 80, 25);
		panel.add(currentLabel);

		JTextArea currentArea = new JTextArea("");
		currentArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		currentArea.setBackground(Color.decode("#CCCCCC"));
		currentArea.setBounds(10, 100, 120, 25);
		panel.add(currentArea);

		JLabel setLabel = new JLabel("Value to be set");
		setLabel.setBounds(140, 140, 150, 25);
		panel.add(setLabel);

		JTextField setText = new JTextField(20);
		setText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setText.setBounds(10, 140, 120, 25);
		setText.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				device.execute("current_set", new Object[] { Double.parseDouble(setText.getText()) });

			}

		});
		panel.add(setText);

		JLabel rampLabel = new JLabel("Ramp values");
		rampLabel.setBounds(140, 180, 150, 25);
		panel.add(rampLabel);

		JTextField rampText = new JTextField(20);
		rampText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		rampText.setBounds(10, 180, 120, 25);
		rampText.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] array = rampText.getText().split("[,\\s]+");
				Object[] rampValues = new Object[array.length];
				for (int i = 0; i < rampValues.length; i++) {
					rampValues[i] = Double.parseDouble(array[i]);
				}
				device.execute("loadRamp", rampValues);
			}
		});
		panel.add(rampText);

		JLabel timeLabel = new JLabel("Time in msecs");
		timeLabel.setBounds(140, 220, 150, 25);
		panel.add(timeLabel);

		JTextField timeText = new JTextField(20);
		timeText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		timeText.setBounds(10, 220, 120, 25);
		timeText.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				msecs = Integer.parseInt(timeText.getText());
			}

		});
		panel.add(timeText);

		JLabel startLabel = new JLabel("Start Ramping");
		startLabel.setBounds(140, 260, 120, 25);
		panel.add(startLabel);

		JButton startButton = new JButton("");
		startButton.setBackground(Color.decode("#999999"));
		startButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		startButton.setBounds(10, 260, 120, 25);
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				device.execute("startRamp", new Object[] { msecs });
			}
		});
		panel.add(startButton);

		JLabel rampStatusLabel = new JLabel("Ramping On/Off");
		rampStatusLabel.setFont(rampStatusLabel.getFont().deriveFont(17.0f));
		rampStatusLabel.setBounds(35, 290, 170, 25);
		panel.add(rampStatusLabel);

		JTextArea logArea = new JTextArea("Log Screen");
		logArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		logArea.setFont(logArea.getFont().deriveFont(24.0f));
		logArea.setBackground(Color.WHITE);
		logArea.setBounds(10, 320, 230, 130);
		panel.add(logArea);

	}

}