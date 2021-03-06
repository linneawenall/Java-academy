package com.cosylab.jwenall.academy.problem5;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class NewBoard extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Shape> shapeList;
	Timer timer; // fires one or more action events after a specified delay
	// for consistent rendering
	private long sleepTime;
	// amount of time to sleep for (in milliseconds)
	private long delay = 30;

	public NewBoard() {
		shapeList = new ArrayList<Shape>();
		timer = new Timer((int) delay, this);
		timer.start();

	}

	// When you create the timer, you specify an action listener to be notified
	// when the timer "goes off"
	@Override
	public void actionPerformed(ActionEvent e) {
		long beforeTime = System.nanoTime();
		for (int i = 0; i < shapeList.size(); i++) {
			shapeList.get(i).moveShape();
		}
		repaint();
		sleepTime = delay - ((System.nanoTime() - beforeTime) / 1000000L);
		try {
			// actual sleep code
			if (sleepTime > 0) {
				Thread.sleep(sleepTime);
			}
		} catch (InterruptedException ex) {

		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		for (Shape shape : shapeList)
			shape.drawShape(g2D);

	}

	public void launchShape(String shapeType) {
		Shape shape;
		try {
			shape = (Shape) Class.forName(shapeType).newInstance();
			timer.setDelay((int) shape.getSpeed());
			shape.shapeSet(getWidth(), getHeight());
			shapeList.add(shape);

		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}
