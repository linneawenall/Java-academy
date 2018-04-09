package com.cosylab.jwenall.academy.problem7;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private static void createAndShowGUI() {
		// Make sure we have nice window decorations.
		JFrame.setDefaultLookAndFeelDecorated(true);

		// Create and set up the window.
		JFrame frame = new JFrame("Drag and drop label");
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		JPanel panel = new JPanel();
		
		DragAndDrop dNd = new DragAndDrop();
		//panel.setDropTarget();
		dNd.placeComponents(panel);
		dNd.setOpaque(true);

		frame.add(panel);
		// Display the window.
		frame.setVisible(true);
	}
}