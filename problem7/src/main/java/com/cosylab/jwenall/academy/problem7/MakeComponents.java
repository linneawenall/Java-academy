package com.cosylab.jwenall.academy.problem7;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.TransferHandler;
import javax.swing.JLabel;

public class MakeComponents extends JFrame {

	private JPanel panel;
	private JLabel label;
	private JTextField textField;
	private static final long serialVersionUID = 3313822387111573465L; // why do
																		// I
																		// need
																		// this?

	public MakeComponents() {

	}

	public void placeComponents(JPanel panel) {
		this.panel = panel;
		panel.add(makeLabel());
		panel.add(makeTextField());

	}

	public JTextField makeTextField() {
		textField = new JTextField(30);
		textField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		textField.setBounds(30, 30, 50, 25);
		textField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}

		});
		return textField;

	}

	public JLabel makeLabel() {
		label = new JLabel("Drag me to panel or textField");
		label.setOpaque(true);
		label.setBackground(Color.WHITE);
		label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		label.setBounds(100, 100, 120, 25);

		label.setTransferHandler(new TransferHandler(""));
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent mEvt) {
				JComponent component = (JComponent) mEvt.getSource();
				TransferHandler tHandler = component.getTransferHandler();
				tHandler.exportAsDrag(component, mEvt, TransferHandler.COPY);
			}
		});
		return label;

	}

}
