package com.cosylab.jwenall.academy.problem4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.cosylab.jwenall.academy.problem3.NarrowRampedPowerSupplyImpl;

public class MyApplication extends JFrame {
	private JLabel currentValue;
	private NarrowRampedPowerSupplyImpl nrps;
	private Double[] rampValues;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MyApplication app = new MyApplication();
				app.setVisible(true);
			}
		});
	}

	private MyApplication() {
		// create UI here: add buttons, actions etc
		this.currentValue = new JLabel("Current value");
		JTextField setValue = new JTextField("Value to be set");
		setValue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] params = (Object[]) new Object();
				params[0] = setValue.getText(); // adds a string. might need to
												// be double
				nrps.execute("current_set", params);
			}
		});

		JTextField rampVal = new JTextField("Ramp Values");
		setValue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] params = (Object[]) new Object();
				params = rampVal.getText().split("[,\\s]+"); // gives String
																// array. might
																// need fixing
				this.rampValues = nrps.execute("loadRamp", params);//loadramp method is void..
			}
		});
		
		JTextField time = new JTextField("Time in msecs");
		setValue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msecs = time.getText();
			}
		});


		JButton startRamp = new JButton("Start ramping");
		startRamp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread ramperThread = new Thread() {
					public void run() {
						runRamper();
					}
				};
				ramperThread.start();
			}
		});
	}

	// Called from non-UI thread
	private void runRamper() {
		nrps.execute("startRamping", rampVals.getText().split);// rampValues are collected
													// from loadRamp Jtextfield
													// input
		updateRamper(nrps.execute("current_get", new Object[] {}));
	}

	private void updateRamper(double currentValue) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Here, we can safely update the GUI
				// because we'll be called from the
				// event dispatch thread
				currentValue.setText("Current: " + currentValue); //need to make currentvalue to string
			}
		});
	}
}
