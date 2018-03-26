package com.cosylab.jwenall.academy.problem5;

import javax.swing.*;



import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;


public class Board extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Shape> shapeList;
	private boolean hasShapes;
	private InitMove movingShapes;
	private Thread moverThread;

	public Board() {
		super();
		setPreferredSize(new Dimension(500, 300));
		shapeList = new ArrayList<Shape>();
		moverThread = new Thread(new Mover());
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				for (Shape shape : shapeList) {
					shape.setBoardBounds(getWidth(), getHeight());
				}
			}
		});
	}

	public void launchShape(String shapeType) {
		if (!hasShapes) {
			initMove();
			hasShapes = true;
		}
		Shape shape;
		try {
			shape = (Shape) Class.forName(shapeType).newInstance();
			shape.shapeSet(getWidth(), getHeight());
			shapeList.add(shape);
			for (int i = 0; i < shapeList.size(); i++) {
				System.out.println("Shape at index " + i + ": " + shapeList.get(i).getShapeType());
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void initMove() {
		movingShapes = new InitMove();
		movingShapes.execute();
	}

	// "The moving of the objects must be handled in a separate thread"

	private class InitMove extends SwingWorker<Void, Void> {
		@Override
		protected Void doInBackground() throws Exception {
			while (true) {
				publish();
				moveShapes();
				//Thread.sleep(10);
				//System.out.println("Repaints board");
				//repaint();
				return null;
			}
		}

		@Override
		protected void process(List<Void> list) {
			System.out.println("Repaints board");
			repaint();

		}

	};

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		for (Shape shape : shapeList)
			shape.drawShape(g2D);

	}

	private void moveShapes() {
//		SwingUtilities.invokeLater(new Runnable() {
//			@Override
//			public void run() {
//				for (Shape shape : shapeList) {
//					System.out.println(shape.getShapeType() + " has been moved");
//					shape.moveShape();
//				}
//			}
//		});
		moverThread.start();
	}
	
	private class Mover implements Runnable {

		@Override
		public void run()  {
			//System.out.println("Mover is running");
			for (int i = 0; i <= shapeList.size() - 1; i++) {
				shapeList.get(i).moveShape();
				System.out.println(shapeList.get(i).getShapeType() +"has been moved in Mover");
				
				//Thread.sleep(msecs);
			}

		}
	}
}

