
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
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
import javax.swing.SwingWorker;

public class PanelClient implements ActionListener {
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
	// private CurrentValueFinder cvf;

	// Connection
	private static Socket clientSocket;
	private static final int PORT = 4444;

	// private static String fromServer;
	private Command fromUser;
	private Object[] fromServer;

	private Object[] params;

	private Command lastCommand = null;

	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException {
		JFrame frame = new JFrame("PowerSupply Panel");
		frame.setSize(265, 497);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setBackground(Color.decode("#CCCCFF"));
		frame.add(panel);
		PanelClient comp = new PanelClient();
		comp.placeComponents(panel);
		frame.setVisible(true);
		comp.initConnection();

	}

	public void initConnection() throws ClassNotFoundException {
		String hostName = "localhost";
		int portNumber = Integer.parseInt("4444");

		// if (fromServer.equals("off"))
		// break;

		try {
			clientSocket = new Socket(hostName, portNumber);

			ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

			fromUser = new Command("started", null);

			while ((fromServer = (Object[]) in.readObject()) != null) {
				System.out.println("What does server say?");
				for (int i = 0; i < fromServer.length; i++) {
					System.out.print(" index[" + i + "] is " + fromServer[i]);

				}

				System.out.println("");
				updateGUI(fromServer);
				if (fromUser != null) {
					System.out.println("In if statement");

					lastCommand = fromUser;

					System.out.println("Client: " + fromUser.getName());
					out.writeObject(fromUser);
				}
			}

		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostName);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + hostName);
			System.exit(1);
		}
	}

	@Override
	public void actionPerformed(final ActionEvent evt) {

		// Power on
		if (evt.getSource().equals(onButton)) {
			fromUser = new Command("on", null);
			System.out.println("On button pushed");
		} else if (evt.getSource().equals(offButton)) {
			fromUser = new Command("off", null);
			System.out.println("Off button pushed");
		} else if (evt.getSource().equals(resetButton)) {
			fromUser = new Command("reset", null);
			System.out.println("Reset button pushed");
		} else if (evt.getSource().equals(setText)) {
			if (isDouble(setText.getText())) {
				fromUser = new Command("current_set", new Object[] { Double.parseDouble(setText.getText()) });
				System.out.println("Current setText fired");
			} else {
				// Should come from server
				logArea.append("Only numbers");
			}
		} else if (evt.getSource().equals(rampText)) {
			array = rampText.getText().split("[,\\s]+");
			Object[] rampValues = new Object[array.length];
			if (isAllDouble(array)) {
				for (int i = 0; i < rampValues.length; i++) {
					rampValues[i] = Double.parseDouble(array[i]);
				}
				fromUser = new Command("loadRamp", rampValues);
			} else {
				// Should come from Server
				logArea.append("Only numbers");
			}
		} else if (evt.getSource().equals(timeText)) {
			// These things should be done in WorkerRunnable
			if (isInteger(timeText.getText())) {
				msecs = Integer.parseInt(timeText.getText());
				logArea.append("Ramping time set to: " + msecs + " msecs \n");
			} else {
				logArea.append("Error: Only one number accepted \n");

			}
		} else if (evt.getSource().equals(startButton)) {
			fromUser = new Command("startRamp", new Object[] { msecs });

		}
	}

	public PanelClient() throws FileNotFoundException {
		device = new NarrowRampedPowerSupplyImpl(new RampedPowerSupplyImpl());
		deviceIcon = createImageIcon("/red.png", "Red dot");
		rampIcon = createImageIcon("/red.png", "Red dot");

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
		onButton.addActionListener(this);
		return onButton;
	}

	public JButton makeOffButton() {
		offButton = new JButton("Off");
		offButton.setBackground(Color.decode("#999999"));
		offButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		offButton.setFont(offButton.getFont().deriveFont(17.0f));
		offButton.setBounds(89, 50, 60, 25);
		offButton.addActionListener(this);
		return offButton;
	}

	public JButton makeResetButton() {
		resetButton = new JButton("Reset");
		resetButton.setBackground(Color.decode("#999999"));
		resetButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		resetButton.setFont(resetButton.getFont().deriveFont(17.0f));
		resetButton.setBounds(166, 50, 60, 25);

		resetButton.addActionListener(this);
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
		setText.addActionListener(this);
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
		rampText.addActionListener(this);
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
		timeText.addActionListener(this);
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

		startButton.addActionListener(this);
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

	// private ImageIcon whichIcon(String command) {
	// ImageIcon red = createImageIcon("/red.png", "Red dot");
	// ImageIcon green = createImageIcon("/green.png", "Green dot");
	// if (command.equals("on") || command.equals("startRamp")) {
	// return green;
	// }
	// return red;
	// }
	private ImageIcon whichIcon(boolean isOn) {
		ImageIcon red = createImageIcon("/red.png", "Red dot");
		ImageIcon green = createImageIcon("/green.png", "Green dot");
		if (isOn) {
			return green;
		}
		return red;
	}

	// private void runCurrent() {
	// rampStatusLabel.setIcon(whichIcon(true));
	// cvf = new CurrentValueFinder();
	// cvf.execute();
	// }
	//
	// private class CurrentValueFinder extends SwingWorker<Void, Void> {
	// @Override
	// protected Void doInBackground() throws Exception {
	// for (int i = 0; i <= array.length; i++) {
	// currentLabel.setText(device.execute("current_get", new Object[]
	// {}).toString());
	// Thread.sleep(msecs);
	// if (i == array.length - 1) {
	// rampStatusLabel.setIcon(whichIcon(false));
	// logArea.append("Ramping completed \n");
	// }
	// }
	// return null;
	// }
	// };

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

	public void updateGUI(Object[] fromServer) {
		if ((String) fromServer[0] != null) {
			System.out.println("UdtateGUI: currentLabel " + (String) fromServer[0]);
			currentLabel.setText((String) fromServer[0]);
		}
		if ((String) fromServer[1] != null) {
			System.out.println("UdtateGUI: logArea " + (String) fromServer[1]);
			logArea.append((String) fromServer[1] + "\n");
		}

		System.out.println("UpdateGUI: icon " + fromServer[2]);
		deviceLabel.setIcon(whichIcon((boolean) fromServer[2]));
		rampStatusLabel.setIcon(whichIcon((boolean) fromServer[3]));

	}
}
