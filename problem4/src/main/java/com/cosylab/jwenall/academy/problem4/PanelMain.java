package com.cosylab.jwenall.academy.problem4;

import java.awt.Color;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PanelMain {

	public static void main(String[] args) throws FileNotFoundException {
		JFrame frame = new JFrame("PowerSupply Panel");
		frame.setSize(265, 497);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setBackground(Color.decode("#CCCCFF"));
		frame.add(panel);
		MakeComponents comp = new MakeComponents(panel);
		frame.setVisible(true);
	}

	

}
