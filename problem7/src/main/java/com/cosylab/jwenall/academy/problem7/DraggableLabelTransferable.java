package com.cosylab.jwenall.academy.problem7;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class DraggableLabelTransferable implements Transferable {
	private final DataFlavor dataFlavor = new DataFlavor(DraggableLabel.class, "DraggableLabel");
	private DraggableLabel draggableLabel;
	private final DataFlavor[] okFlavors = { DataFlavor.stringFlavor, dataFlavor };

	public DraggableLabelTransferable(DraggableLabel label) {
		this.draggableLabel = label;
	}

	// Returns an object which represents the data to be transferred.
	@Override
	public Object getTransferData(DataFlavor df) throws UnsupportedFlavorException, IOException {
		if (df.equals(DataFlavor.stringFlavor)) {
			System.out.println("DraggableLabelTransferable: The text is returned");
			return draggableLabel.getText();
		} else if (df.equals(dataFlavor)) {
			try {
				System.out.println("DraggableLabelTransferable: A clone is created");
				return draggableLabel.clone();
			} catch (CloneNotSupportedException e) {

			}
		}
		return null;

	}

	// Returns an array of DataFlavor objects indicating the flavors the data can be
	// provided in.
	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return okFlavors;
	}

	// Returns whether or not the specified data flavor is supported for this
	// object.
	@Override
	public boolean isDataFlavorSupported(DataFlavor df) {
		for (DataFlavor supportFlavor : okFlavors) {
			if (supportFlavor.equals(df)) {
				return true;
			}
		}
		return false;
	}

	public DataFlavor getDataFlavor() {
		return dataFlavor;
	}

}
