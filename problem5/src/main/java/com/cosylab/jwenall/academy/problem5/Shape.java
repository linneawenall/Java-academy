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

	private double speed;

	protected double shapeWidth = 20;
	protected double shapeHeight = 20;

	protected Color color;

	public void shapeSet(int boardWidth, int boardHeight) {
		setBoardBounds(boardWidth, boardHeight);
		setShapeSize(shapeWidth, shapeHeight);
		randomPosition();
		setRandomSpeed();
		randomDirection();
		setRandomColor();
	}

	public abstract void drawShape(Graphics2D g2D); // implement in other
													// classes

	// checks if it hit's the board wall
	public void moveShape() {
		// double shapeMinX = shapeWidth;
		// double shapeMinY = shapeHeight;
		double shapeMaxX = boardWidth - shapeWidth;
		double shapeMaxY = boardHeight - shapeHeight;
		// Calculate the shapes's new position
		posX += moveX;
		posY += moveY;
		// Check if the shape moves over the bounds. If so, adjust the position
		// and speed.
		if (posX < 0) {
			moveX = -moveX; // Reflect along normal
			posX = 0; // Re-position the shape at the edge
		} else if (posX > shapeMaxX) {
			moveX = -moveX;
			posX = shapeMaxX;
		}
		// May cross both x and y bounds
		if (posY < 0) {
			moveY = -moveY;
			posY = 0;
		} else if (posY > shapeMaxY) {
			moveY = -moveY;
			posY = shapeMaxY;
		}
	}

	public double getXpos() {
		return posX;
	}

	public double getYpos() {
		return posY;
	}

	private void randomPosition() {
		posX = (double) (Math.random() * boardWidth);
		posY = (double) (Math.random() * boardHeight);
	}

	private void setRandomSpeed() {
		speed = Math.random() * 4; // returns a number from zero to one * 2
		System.out.println("speed: " + speed);

	}

	public double getSpeed() {
		return (double) Math.sqrt(moveX * moveX + moveY * moveY);
	}

	private void randomDirection() {
		double direction = Math.random() * 2.0 * Math.PI;
		System.out.println("direction: " + direction);
		moveX = (speed * Math.cos(direction));
		System.out.println("moveX: " + moveX);
		moveY = (speed * Math.sin(direction));
		System.out.println("moveY: " + moveY);
	}

	private void setBoardBounds(int width, int height) {
		this.boardWidth = width;
		this.boardHeight = height;
	}

	private void setShapeSize(double shapeWidth, double shapeHeight) {
		this.shapeWidth = shapeWidth;
		this.shapeHeight = shapeHeight;
	}

	private void setRandomColor() {
		int rgb1 = new Random().nextInt(255) + 1;
		int rgb2 = new Random().nextInt(255) + 1;
		int rgb3 = new Random().nextInt(255) + 1;
		color = new Color(rgb1, rgb2, rgb3);
	}

}
