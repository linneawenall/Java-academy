package com.cosylab.jwenall.academy.problem7;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.TransferHandler;
import javax.swing.TransferHandler.TransferSupport;

public class DropTargetPanel extends JPanel implements DropTargetListener {
	private DraggableLabel dragLabel;

	public DropTargetPanel(DraggableLabel dragLabel) {
		this.dragLabel = dragLabel;

		new DropTarget(this, this);
	}

	public void dragEnter(DropTargetDragEvent evt) {

		// Called when the user is dragging and enters this drop target

		System.out.println("Drop enter");
	}

	public void dragOver(DropTargetDragEvent evt) {

		// Called when the user is dragging and moves over this drop target

		System.out.println("Drop over");
	}

	public void dragExit(DropTargetEvent evt) {

		// Called when the user is dragging and leaves this drop target

		System.out.println("Drop exit");
	}

	public void dropActionChanged(DropTargetDragEvent evt) {

		// Called when the user changes the drag action between copy or move

		System.out.println("Drop action changed");
	}

	public void drop(DropTargetDropEvent evt) {

		// Called when the user finishes or cancels the drag operation

//		try {
			DraggableLabelTransferable transferable = new DraggableLabelTransferable(dragLabel);
			DroppablePanelTransferHandler transferHandler = new DroppablePanelTransferHandler(this);
			if (transferable.isDataFlavorSupported(transferable.getDataFlavor())) {

				evt.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
//				DraggableLabel dragContents = (DraggableLabel) transferable
//					.getTransferData(transferable.getDataFlavor());
//				Point p = evt.getLocation();
//				System.out.println("New label placed at: "+p.getX()+" , " +p.getY());
//				dragContents.setLocation(p);
//				add(dragContents);
//				repaint();
//				validate();
				//Fix here. How to get the Transfersupport parameter?
				TransferSupport transferSupport = new TransferSupport(this, transferable);
				//This returns false, but how do I make it return true?
				System.out.println(transferSupport.isDrop());
				transferHandler.importData(transferSupport);
				evt.getDropTargetContext().dropComplete(true);
			} else {
				evt.rejectDrop();
			}
//		} catch (
//
//		IOException e) {
//			evt.rejectDrop();
//
//		} catch (UnsupportedFlavorException e) {
//			evt.rejectDrop();
//
//		}
	}

}
