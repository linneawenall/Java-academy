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

public class Components extends JFrame {
	private DeviceNarrow device;
	private int msecs;
	private JLabel deviceLabel;
	private ImageIcon deviceIcon;
	private ImageIcon rampIcon;
	private JTextArea currentArea;
	private String[] array;


	public Components(JPanel panel) throws FileNotFoundException {
		device = new NarrowRampedPowerSupplyImpl(new RampedPowerSupplyImpl());
		deviceIcon = createImageIcon("/res/red.png", "Red dot");
		rampIcon = createImageIcon("/res/red.png", "Red dot");
		currentArea = new JTextArea("");
		placeComponents(panel);

	}

	private void placeComponents(JPanel panel) throws FileNotFoundException {

		panel.setLayout(null);

		deviceLabel = new JLabel("Device On/Off", deviceIcon, JLabel.LEFT);
		deviceLabel.setFont(deviceLabel.getFont().deriveFont(17.0f));
		deviceLabel.setBounds(10, 10, 170, 25);
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
				deviceLabel.setIcon(whichIcon("on"));
				//setCurrentArea("on");
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
				deviceLabel.setIcon(whichIcon("off"));
				//setCurrentArea("off");

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
				//setCurrentArea("reset");
			}

		});
		panel.add(resetButton);

		JLabel currentLabel = new JLabel("Current Value");
		currentLabel.setBounds(140, 100, 80, 25);
		panel.add(currentLabel);

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
				//setCurrentArea("current_set");

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
				array = rampText.getText().split("[,\\s]+");
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

		JLabel rampStatusLabel = new JLabel("Ramping On/Off", rampIcon, JLabel.LEFT);
		rampStatusLabel.setFont(rampStatusLabel.getFont().deriveFont(17.0f));
		rampStatusLabel.setBounds(10, 290, 170, 25);
		panel.add(rampStatusLabel);

		JButton startButton = new JButton("");
		startButton.setBackground(Color.decode("#999999"));
		startButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		startButton.setBounds(10, 260, 120, 25);
//		startButton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				device.execute("startRamp", new Object[] { msecs });
//			//	setCurrentArea("startRamp");
//				rampStatusLabel.setIcon(whichIcon("startRamp"));
//			}
//		});
		
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
		panel.add(startButton);

		JTextArea logArea = new JTextArea("Log Screen");
		logArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		logArea.setFont(logArea.getFont().deriveFont(24.0f));
		logArea.setBackground(Color.WHITE);
		logArea.setBounds(10, 320, 230, 130);
		panel.add(logArea);

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

//	private void setCurrentArea(String command) {
//		if (command.equals("off")) {
//			currentArea.setText("0.0");
//
//		}
//		(new updateCurrent()).execute();
//
//	}

//	private class updateCurrent extends SwingWorker<String, Object> {
//		@Override
//		public String doInBackground() {
//			return device.execute("current_get", new Object[] {}).toString();
//		}
//
//		@Override
//		protected void done() {
//			try {
//				currentArea.setText(get());
//			} catch (Exception ignore) {
//			}
//		}
//	}
	//------------------------------------------------------

	


	// Called from non-UI thread
	private void runCurrent() {
		//device.execute("startRamp", new Object[] { msecs });
	//	updateProgress(device.execute("current_get", new Object[] {}).toString());
		(new CurrentValueFinder()).execute();
	}
	
	


//	private void updateProgress(final String currentValue) {
//		SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
//				// Here, we can safely update the GUI
//				// because we'll be called from the
//				// event dispatch thread
//				currentArea.setText(currentValue);
//			}
//		});
//	}
	
	private class CurrentValueFinder extends SwingWorker<Void, Void> {
         @Override
         protected Void doInBackground() throws Exception {
             for (int i = 0; i <= array.length; i++) {
                 currentArea.setText(device.execute("current_get", new Object[] {}).toString());
                 Thread.sleep(msecs);
             }

             return null;
         }
     };

}
