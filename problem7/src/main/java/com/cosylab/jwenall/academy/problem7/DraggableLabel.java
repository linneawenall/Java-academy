package com.cosylab.jwenall.academy.problem7;
import java.awt.Label;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;

import javax.swing.JLabel;

// Make a Label draggable; You can use the example to make any component
// draggable
public class DraggableLabel extends JLabel implements DragGestureListener, DragSourceListener, Cloneable {
	DragSource dragSource;

	public DraggableLabel(String text) {

		setText(text);

		dragSource = new DragSource();

		dragSource.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY_OR_MOVE, this);
	}

	public void dragGestureRecognized(DragGestureEvent evt) {
		//LOOK HERE - only accepts string transferable
		//Transferable transferable = new StringSelection(getText());
		Transferable transferable = new DraggableLabelTransferable(this);

		dragSource.startDrag(evt, DragSource.DefaultCopyDrop, transferable, this);
	}

	public void dragEnter(DragSourceDragEvent evt) {

		// Called when the user is dragging this drag source and enters the drop target

		System.out.println("Drag enter");
	}

	public void dragOver(DragSourceDragEvent evt) {

		// Called when the user is dragging this drag source and moves over the drop
		// target

		System.out.println("Drag over");
	}

	public void dragExit(DragSourceEvent evt) {

		// Called when the user is dragging this drag source and leaves the drop target

		System.out.println("Drag exit");
	}

	public void dropActionChanged(DragSourceDragEvent evt) {

		// Called when the user changes the drag action between copy or move

		System.out.println("Drag action changed");
	}

	public void dragDropEnd(DragSourceDropEvent evt) {

		// Called when the user finishes or cancels the drag operation

		System.out.println("Drag action End");
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
