package com.cosylab.jwenall.academy.problem8;

import javax.swing.JTextArea;

public class DocumentTextArea extends JTextArea {

	// Serial version UID
	private static final long serialVersionUID = 7665635049178799501L;

	public void update(String line) {
		append(line);
	}

	public void clean() {
		setText("");
	}
}
