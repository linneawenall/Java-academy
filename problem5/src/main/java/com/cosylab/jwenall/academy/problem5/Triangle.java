package com.cosylab.jwenall.academy.problem5;

import java.awt.Graphics2D;

import java.awt.geom.GeneralPath;

public class Triangle extends Shape {

	private double sideLength;
	private double height;

	// "To create a new construct, e.g. a star, only Star class handling the
	// drawing of the star must be implemented. " Does that mean I'm not allowed
	// this shapeSet method? And why does everything work without constructors?
	@Override
	public void shapeSet(int boardWidth, int boardHeight) {

		sideLength = 20; // "a few pixels in size"
		height = (Math.sqrt(3) * sideLength) / 2;
		setShapeSize(sideLength, height);
		super.shapeSet(boardWidth, boardHeight);
	}

	@Override
	public void drawShape(Graphics2D g2d) {
		GeneralPath triangle = new GeneralPath(GeneralPath.WIND_EVEN_ODD); // Path2D
																			// methods
																			// pos
		triangle.moveTo(getXpos(), (getYpos() + height)); // the pos where to
															// start drawing
		triangle.lineTo(getXpos() + sideLength, getYpos() + height);
		triangle.lineTo(getXpos() + (sideLength), getYpos());
		triangle.closePath(); // draws line back to the last moveTo

		g2d.setColor(color);
		g2d.fill(triangle); // fills interior
		g2d.draw(triangle); // Strokes the outline of a Shape
		System.out.println(getShapeType() + " has been drawn in Class Triangle");

	}

	@Override
	public String getShapeType() {
		return "Triangle";
	}

}