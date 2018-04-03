package com.cosylab.jwenall.academy.problem5;

import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;

public class Diamond extends Shape {


	@Override
	public void drawShape(Graphics2D g2d) {
		GeneralPath diamond = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
		diamond.moveTo(getXpos(), (getYpos() + shapeHeight / 2));
		diamond.lineTo(getXpos() + (shapeWidth / 2), getYpos() + shapeHeight);
		diamond.lineTo(getXpos() + shapeWidth, getYpos() + shapeHeight / 2);
		diamond.lineTo(getXpos() + (shapeWidth / 2), getYpos());

		g2d.setColor(color);
		g2d.fill(diamond); // fills interior
		g2d.draw(diamond); // Strokes the outline of a Shape
	}

}
