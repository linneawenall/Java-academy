package com.cosylab.jwenall.academy.problem5;

import java.awt.Graphics2D;

import java.awt.geom.GeneralPath;

public class Triangle extends Shape {


	@Override
	public void drawShape(Graphics2D g2d) {
		GeneralPath triangle = new GeneralPath(GeneralPath.WIND_EVEN_ODD); // Path2D
																			// methods
																			// pos
		triangle.moveTo(getXpos(), (getYpos() + shapeHeight)); // the pos where to
															// start drawing
		triangle.lineTo(getXpos() + shapeWidth, getYpos() + shapeHeight);
		triangle.lineTo(getXpos() + shapeWidth, getYpos());
		triangle.closePath(); // draws line back to the last moveTo

		g2d.setColor(color);
		g2d.fill(triangle); // fills interior
		g2d.draw(triangle); // Strokes the outline of a Shape


	}


}