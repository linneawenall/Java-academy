package com.cosylab.jwenall.academy.problem7;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
//		SwingUtilities.invokeLater(new Runnable() {
//			@Override
//			public void run() {
//				JFrame frame = new JFrame("Board");
//				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//				frame.setLayout(new BorderLayout());
//				Label label = new Label();
//				frame.add(label);
//				frame.setLocationRelativeTo(null);
//				frame.setVisible(true);
//			}
//		});
//	}
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame frame = new JFrame("Board");
				frame.setSize(400, 400);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLayout(new BorderLayout());
				JPanel panel = new JPanel();
				MakeComponents comp = new MakeComponents();
				comp.placeComponents(panel);
				frame.add(panel);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}

}
