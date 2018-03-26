package com.cosylab.jwenall.academy.problem5;

import java.awt.Graphics2D;

public class Circle extends Shape {
	private double radius;

	@Override
	public void shapeSet(int boardWidth, int boardHeight) {
		radius = 20; // "a few pixels in size"
		setShapeSize(2 * radius, 2 * radius);
		super.shapeSet(boardWidth, boardHeight);
	}

	@Override
	public void drawShape(Graphics2D g2d) {
		g2d.setColor(color);
		// Fills an oval bounded by the specified rectangle with the current
		// color.
		g2d.fillOval((int) getXpos(), (int) getYpos(), 2 * (int) radius, 2 * (int) radius);
		g2d.drawOval((int) getXpos(), (int) getYpos(), 2 * (int) radius, 2 * (int) radius);
		System.out.println(getShapeType() + " has been drawn in class Circle");

	}
	@Override
	public String getShapeType(){
		return "Circle";
	}

}
