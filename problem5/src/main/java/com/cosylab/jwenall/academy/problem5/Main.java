package com.cosylab.jwenall.academy.problem5;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		JFrame frame = new JFrame("Board");
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Board board = new Board();
		Components comp = new Components();
		comp.placeComponents(board);
		board.setBackground(Color.decode("#CCCCCC"));
		frame.add(board);
		frame.setVisible(true);
	}}