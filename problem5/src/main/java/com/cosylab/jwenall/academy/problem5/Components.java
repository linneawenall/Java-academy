package com.cosylab.jwenall.academy.problem5;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

public class Components extends JFrame {

	private Board board;
	private JComboBox<String> shapeComboBox;
	private JButton launchButton;
	private String name;
	private static final long serialVersionUID = 3313822387111573465L; //why do I need this?

	public Components() {
		name = Shape.class.getPackage().getName();
	}

	public void placeComponents(Board board) {
		this.board = board;
		board.setBorder(BorderFactory.createTitledBorder("Control Board"));
		shapeComboBox = new JComboBox<String>();
		shapeComboBox.addItem("Box");
		shapeComboBox.addItem("Circle");
		shapeComboBox.addItem("Diamond");
		shapeComboBox.addItem("Triangle");


		launchButton = new JButton("Launch");
		launchButton.setBackground(Color.decode("#999999"));
		launchButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		launchButton.setFont(launchButton.getFont().deriveFont(17.0f));
		launchButton.setBounds(10, 50, 60, 25);
		launchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				board.launchShape(name+"."+(String)shapeComboBox.getSelectedItem());
				System.out.println("Launch has been pressed");
			}

		});
		putComponents();

	}

	private void putComponents() {
		board.add(shapeComboBox);
		board.add(launchButton);
	}

}
