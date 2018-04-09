package com.cosylab.jwenall.academy.problem7;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DnDMain {

	public static void main(String[] args) {

		// Create a frame
		JFrame frame = new JFrame("Example Frame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/*
		 * 
		 * Create a container with a flow layout, which arranges its children
		 * 
		 * horizontally and center aligned. A container can also be created with
		 * 
		 * a specific layout using Panel(LayoutManager) constructor, e.g.
		 * 
		 * Panel(new FlowLayout(FlowLayout.RIGHT)) for right alignment
		 * 
		 */
		// Panel panel = new Panel();

		// Add a drop target text area in the center of the frame
		JTextField textField = new DropTargetTextField();
		textField.setMinimumSize(new Dimension(300,300));
		frame.add(textField);

		// Add several draggable labels to the container
		DraggableLabel helloLabel = new DraggableLabel("Hello");
		// JLabel worldLabel = new DraggableLabel("World");
		JPanel panel = new DropTargetPanel(helloLabel);
		panel.setMinimumSize(new Dimension(300,300));

		panel.add(helloLabel);
		// panel.add(worldLabel);

		// Add the container to the bottom of the frame
		frame.add(panel, BorderLayout.EAST);

		// Display the frame
		int frameWidth = 300;
		int frameHeight = 300;
		frame.setSize(2 * frameWidth, frameHeight);

		frame.setVisible(true);

	}
}