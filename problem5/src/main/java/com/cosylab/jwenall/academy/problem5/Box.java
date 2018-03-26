package com.cosylab.jwenall.academy.problem5;

import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;

public class Box extends Shape {
	private double sideLength;

	@Override
	public void shapeSet(int boardWidth, int boardHeight) {
		sideLength = 20; // "a few pixels in size"
		setShapeSize(sideLength, sideLength);
		super.shapeSet(boardWidth, boardHeight);
	}

	@Override
	public void drawShape(Graphics2D g2d) {
		GeneralPath box = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
		box.moveTo(getXpos(), getYpos());
		box.lineTo(getXpos() + sideLength, getYpos());
		box.lineTo(getXpos() + sideLength, getYpos() + sideLength);
		box.lineTo(getXpos(), getYpos() + sideLength);
		box.closePath(); // draws line back to the last moveTo

		g2d.setColor(color);
		g2d.fill(box); // fills interior
		g2d.draw(box); // Strokes the outline of a Shape
		System.out.println(getShapeType() + " has been drawn");

	}
	@Override
	public String getShapeType(){
		return "Box";
	}

}
