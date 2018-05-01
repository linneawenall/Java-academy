
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.text.DefaultCaret;

public class PanelClient implements ActionListener {

	private JLabel currentLabel, deviceLabel, descriptionLabel, setLabel, rampLabel, timeLabel, startLabel,
			rampStatusLabel;
	private ImageIcon deviceIcon;
	private ImageIcon rampIcon;
	private JTextArea logArea;

	private JButton onButton, offButton, resetButton, startButton;
	private JTextField timeText, setText, rampText;

	// Connection
	private static Socket clientSocket;
	private static final int PORT = 4444;
	private boolean isConnected;
	private static ObjectOutputStream out;
	private ObjectInputStream in;

	private static Command fromUser;
	private Object[] fromServer;
	private String[] array;
	protected int nbrClients;

	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame frame = new JFrame("PowerSupply Panel");
				frame.setSize(265, 497);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				frame.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						fromUser = new Command("disconnect", null);
						if (!clientSocket.isClosed()) {
							try {
								out.writeObject(fromUser);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							e.getWindow().dispose();
						}
					}
				});

				JPanel panel = new JPanel();
				panel.setBackground(Color.decode("#CCCCFF"));
				frame.add(panel);
				PanelClient comp;
				try {
					comp = new PanelClient();
					comp.placeComponents(panel);
					frame.setVisible(true);
					comp.initConnection();
				} catch (FileNotFoundException | ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});

	}

	public PanelClient() throws FileNotFoundException {
		deviceIcon = createImageIcon("/red.png", "Red dot");
		rampIcon = createImageIcon("/red.png", "Red dot");

	}

	public void initConnection() throws ClassNotFoundException {
		try {

			clientSocket = new Socket("localhost", PORT);

			out = new ObjectOutputStream(clientSocket.getOutputStream());

			in = new ObjectInputStream(clientSocket.getInputStream());

			executeOnServer(new Command("Can client connect?", null));

		} catch (UnknownHostException e) {
			System.err.println("Don't know about host localhost ");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to localhost ");
			System.exit(1);
		}
	}

	@Override
	public void actionPerformed(final ActionEvent evt) {

		if (evt.getSource().equals(onButton)) {
			fromUser = new Command("on", null);

		} else if (evt.getSource().equals(offButton)) {
			fromUser = new Command("off", null);

		} else if (evt.getSource().equals(resetButton)) {
			fromUser = new Command("reset", null);

		} else if (evt.getSource().equals(setText)) {
			fromUser = new Command("current_set", new String[] { setText.getText() });

		} else if (evt.getSource().equals(rampText)) {
			array = rampText.getText().split("[,\\s]+");
			fromUser = new Command("loadRamp", array);

		} else if (evt.getSource().equals(startButton)) {
			fromUser = new Command("startRamp", new String[] { timeText.getText() });

		}

		System.out.println("command sent " + fromUser.getName());
		executeOnServer(fromUser);
	}

	public void executeOnServer(Command command) {

		if (!clientSocket.isClosed()) {
			try {
				out.writeObject(command);
				fromServer = (Object[]) in.readObject();
				updateGUI(fromServer);
				if (!isConnected) {
					clientSocket.close();
				}

				if (command.getName().equals("startRamp")) {
					CurrentValueFinder cvf = new CurrentValueFinder();
					cvf.execute();
				}

			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	private class CurrentValueFinder extends SwingWorker<Void, Void> {
		@Override
		protected Void doInBackground() throws Exception {
			for (int i = 0; i <= array.length - 1; i++) {
				out.writeObject(new Command("ramping", null));
				fromServer = (Object[]) in.readObject();
				updateGUI(fromServer);
				Thread.sleep(Integer.parseInt(timeText.getText()));
			}
			return null;
		}
	};

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
		panel.add(makeScrollPane(makeLogArea()));

		// Auto scrolls to the bottom of the code
		DefaultCaret caret = (DefaultCaret) logArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

	}

	public JScrollPane makeScrollPane(JTextArea textArea) {
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBackground(Color.WHITE);

		scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBounds(10, 320, 230, 130);
		return scrollPane;
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

	protected ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	private ImageIcon whichIcon(boolean isOn) {
		ImageIcon red = createImageIcon("/red.png", "Red dot");
		ImageIcon green = createImageIcon("/green.png", "Green dot");
		if (isOn) {
			return green;
		}
		return red;
	}

	public void updateGUI(Object[] fromServer) {
		// System.out.println("Updating GUI");
		// System.out.println("Currentvalue is " + (String) fromServer[0]);
		if ((String) fromServer[0] != null) {
			currentLabel.setText((String) fromServer[0]);
		}
		if ((String) fromServer[1] != null) {
			System.out.println("logUpdate " + (String) fromServer[1]);
			logArea.append((String) fromServer[1] + "\n");
		}
		deviceLabel.setIcon(whichIcon((boolean) fromServer[2]));
		rampStatusLabel.setIcon(whichIcon((boolean) fromServer[3]));
		isConnected = (boolean) fromServer[4];

	}
}
