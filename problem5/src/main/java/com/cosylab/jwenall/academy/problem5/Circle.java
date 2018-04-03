package com.cosylab.jwenall.academy.problem5;

import java.awt.Graphics2D;

public class Circle extends Shape {


	@Override
	public void drawShape(Graphics2D g2d) {
		g2d.setColor(color);
		g2d.fillOval((int) getXpos(), (int) getYpos(),  (int) shapeWidth,  (int) shapeHeight);
		g2d.drawOval((int) getXpos(), (int) getYpos(),  (int) shapeWidth,  (int) shapeHeight);
	}

}
