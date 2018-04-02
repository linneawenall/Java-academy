package com.cosylab.jwenall.academy.problem5;

import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;

public class Diamond extends Shape {

	private double sideLength;
	private double height;

	@Override
	public void shapeSet(int boardWidth, int boardHeight) {
		sideLength = 20; // "a few pixels in size"
		height = (Math.sqrt(3) * sideLength);
		setShapeSize(sideLength, height);
		super.shapeSet(boardWidth, boardHeight);
	}

	@Override
	public void drawShape(Graphics2D g2d) {
		GeneralPath diamond = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
		diamond.moveTo(getXpos(), (getYpos() + height / 2));
		diamond.lineTo(getXpos() + (sideLength / 2), getYpos() + height);
		diamond.lineTo(getXpos() + sideLength, getYpos() + height / 2);
		diamond.lineTo(getXpos() + (sideLength / 2), getYpos());

		g2d.setColor(color);
		g2d.fill(diamond); // fills interior
		g2d.draw(diamond); // Strokes the outline of a Shape
		System.out.println(getShapeType() + " has been drawn in Class Diamond");

	}

	@Override
	public String getShapeType() {
		return "Diamond";
	}

}
