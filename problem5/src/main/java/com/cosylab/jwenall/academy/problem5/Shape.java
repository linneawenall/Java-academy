package com.cosylab.jwenall.academy.problem5;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;


public abstract class Shape {
	private double posX;
	private double posY;
	private double moveX;
	private double moveY;

	
	private int boardWidth;
	private int boardHeight;


	protected double shapeWidth;
	protected double shapeHeight;

	protected Color color;

	public void shapeSet(int boardWidth, int boardHeight) {
		setBoardBounds(boardWidth, boardHeight);
		randomPosition();
		randomDirection();

		color = getRandomColor();
	}

	public abstract void drawShape(Graphics2D g2D); // implement in other
													// classes

	// checks if it hit's the board wall
	public void moveShape() {

		double tempX = moveX;
		double tempY = moveY;

		// X-movement
		if (getXpos() + moveX < 0) {
			tempX = posX;
			moveX = moveX * -1;
		} else if (getXpos() + moveX > boardWidth) {
			tempX = boardWidth - posX;
			moveX = moveX * -1;
		}
		// Y-movement
		if (getYpos() + moveY < 0) {
			tempY = posX;
			moveY = moveX * -1;
		} else if (getYpos() + moveY > boardHeight) {
			tempY = boardHeight - posY;
			moveY = moveX * -1;
		}
		System.out.println("moves in X: " +tempX +"moves in Y: " +tempY);
		setPos(getXpos() + tempX, getYpos() + tempY);
	}

	public double getXpos() {
		return posX;
	}

	public double getYpos() {
		return posY;
	}

	public void setPos(double Xpos, double Ypos) {
		this.posX = Xpos;
		this.posY = Ypos;
		System.out.println(getShapeType() +" has been moved in setPos in Class Shape");
	}

	private void randomPosition() {
		posX = (double) (Math.random() * boardWidth);
		posY = (double) (Math.random() * boardHeight);
	}

	private void randomDirection() {
		double direction = Math.random() * 2.0 * Math.PI;
		System.out.println("direction: " +direction);
		double speed = Math.random();
		System.out.println("speed: "+speed);
		moveX =  (speed * Math.cos(direction));
		System.out.println("moveX: " +moveX);
		moveY =  (speed * Math.sin(direction));
		System.out.println("moveY: " +moveY);
	}

	public void setBoardBounds(int width, int height) {
		this.boardWidth = width - (int) shapeWidth;
		this.boardHeight = height - (int) shapeHeight;
	}

	public void setShapeSize(double shapeWidth, double shapeHeight) {
		this.shapeWidth = shapeWidth;
		this.shapeHeight = shapeHeight;
	}

	public Color getRandomColor() {
		int rgb1 = new Random().nextInt(255) + 1;
		int rgb2 = new Random().nextInt(255) + 1;
		int rgb3 = new Random().nextInt(255) + 1;
		return new Color(rgb1, rgb2, rgb3);
	}
	public abstract String getShapeType();
}
