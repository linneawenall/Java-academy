package com.cosylab.jwenall.academy.problem5;

import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;

public class Box extends Shape {


	@Override
	public void drawShape(Graphics2D g2d) {
		GeneralPath box = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
		box.moveTo(getXpos(), getYpos());
		box.lineTo(getXpos() + shapeWidth, getYpos());
		box.lineTo(getXpos() + shapeWidth, getYpos() + shapeWidth);
		box.lineTo(getXpos(), getYpos() + shapeWidth);
		box.closePath(); // draws line back to the last moveTo

		g2d.setColor(color);
		g2d.fill(box); // fills interior
		g2d.draw(box); // Strokes the outline of a Shape


	}


}
