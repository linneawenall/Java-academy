package com.cosylab.jwenall.academy.problem7;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import javax.swing.JPanel;

public class PanelDropTarget extends JPanel implements DropTargetListener {
	protected JPanel pane;
	protected DropTarget dropTarget;
	protected boolean acceptableType; // Indicates whether data is acceptable
	protected DataFlavor targetFlavor; // Flavor to use for transfer

	public PanelDropTarget(JPanel panel) {
		dropTarget = new DropTarget(panel, DnDConstants.ACTION_COPY_OR_MOVE, this, true, null);
	}

	// Called while a drag operation is ongoing, when the mouse pointer enters
	// the operable part of the drop site for the DropTarget registered with
	// this listener.
	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
		DnDUtils.debugPrintln("dragEnter, drop action = " + DnDUtils.showActions(dtde.getDropAction()));

		checkTransferType(dtde);

	}

	// Called while a drag operation is ongoing, when the mouse pointer has
	// exited the operable part of the drop site for the DropTarget registered
	// with this listener.
	@Override
	public void dragExit(DropTargetEvent arg0) {
		// TODO Auto-generated method stub

	}

	// Called when a drag operation is ongoing, while the mouse pointer is still
	// over the operable part of the drop site for the DropTarget registered
	// with this listener.
	@Override
	public void dragOver(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub

	}

	// Called when the drag operation has terminated with a drop on the operable
	// part of the drop site for the DropTarget registered with this listener.
	@Override
	public void drop(DropTargetDropEvent arg0) {
		// TODO Auto-generated method stub

	}

	// Called if the user has modified the current drop gesture.
	@Override
	public void dropActionChanged(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub

	}

	protected void checkTransferType(DropTargetDragEvent dtde) {
		// Only accept a flavor that returns a Component
		acceptableType = false;
		DataFlavor[] fl = dtde.getCurrentDataFlavors();
		for (int i = 0; i < fl.length; i++) {
			Class dataClass = fl[i].getRepresentationClass();

			if (Component.class.isAssignableFrom(dataClass)) {
				// This flavor returns a Component - accept it.
				targetFlavor = fl[i];
				acceptableType = true;
				break;
			}
		}

		DnDUtils.debugPrintln("File type acceptable - " + acceptableType);
	}

	protected boolean isDragAccepted(DropTargetDragEvent dtde){
		int dropAction = dtde.getDropAction();
		int sourceActions = dtde.getSourceActions();
		boolean acceptedDrag;
		
	    DnDUtils.debugPrintln("\tSource actions are "
	            + DnDUtils.showActions(sourceActions) + ", drop action is "
	            + DnDUtils.showActions(dropAction));
	    //Rejects if the object being transferred is not accebtable or the 
	    //operations are not acceptable
	    if(!acceptableType||(sourceActions & DnDConstants.ACTION_COPY_OR_MOVE)==0){
	    	DnDUtils.debugPrintln("Panel Drop target rejecting drag");
	    	dtde.rejectDrag();
	    }else if((dropAction & DnDConstants.ACTION_COPY_OR_MOVE)==0){
	    	DnDUtils.debugPrintln("Panel Drop target offering COPY");
	    	dtde.acceptDrag(DnDConstants.ACTION_COPY);
	    	acceptedDrag = true;
	    }else{
	    	
	    }
}}
