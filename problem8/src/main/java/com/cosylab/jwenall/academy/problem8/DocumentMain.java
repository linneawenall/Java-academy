package com.cosylab.jwenall.academy.problem8;


import java.awt.EventQueue;

import javax.swing.JFrame;


public class DocumentMain extends JFrame {

	// Serial version UID.
	private static final long serialVersionUID = 6304548624386102350L;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new DocumentMain();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public DocumentMain() {
		// Set frame options.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 715, 508);
		setContentPane(new ComponentsPanel());
		setVisible(true);
	}
}