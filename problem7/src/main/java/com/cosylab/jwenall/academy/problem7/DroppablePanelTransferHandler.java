package com.cosylab.jwenall.academy.problem7;


import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.TransferHandler;
import javax.swing.TransferHandler.TransferSupport;

public class DroppablePanelTransferHandler extends TransferHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DropTargetPanel dPanel;
	private DataFlavor draggableLabelFlavor = new DataFlavor(DraggableLabel.class, "DraggableLabel");

	public DroppablePanelTransferHandler(DropTargetPanel dPanel) {
		this.dPanel = dPanel;
	}

	@Override
	public boolean canImport(TransferSupport support) {
		boolean stringFlavor = support.getTransferable().isDataFlavorSupported(DataFlavor.stringFlavor);

		return stringFlavor | isDraggableFlavorSupported(support);
	}

	// Makes our sourceActions only being copy or move as specified in task
	@Override
	public int getSourceActions(JComponent c) {
		return COPY_OR_MOVE;
	}

	@Override
	public boolean importData(TransferSupport support) {
		System.out.println(support.toString());
		if (canImport(support)) {
			DraggableLabel dLabel = null;
			if (isDraggableFlavorSupported(support)) {
				try {
					System.out.println("DroppablePanelTransferHandler: Going into DraggableLabelTransferable");
					dLabel = (DraggableLabel) support.getTransferable().getTransferData(draggableLabelFlavor);
				} catch (UnsupportedFlavorException | IOException e) {
					System.out.println(e.getMessage());
				}
			} else {
				try {
					String text = (String) support.getTransferable().getTransferData(DataFlavor.stringFlavor);
					dLabel = new DraggableLabel(text);

				} catch (UnsupportedFlavorException | IOException e) {
					System.out.println(e.getMessage());
				}
			}
			//JTable.DropLocation dl = (JTable.DropLocation) support.getDropLocation();
			dLabel.setLocation(support.getDropLocation().getDropPoint().x, support.getDropLocation().getDropPoint().y);
			dPanel.add(dLabel);
			dPanel.repaint();
			return true;
		}
		return false;

	}

	protected boolean isDraggableFlavorSupported(TransferSupport support) {
		return support.getTransferable().isDataFlavorSupported(draggableLabelFlavor);
	}
}