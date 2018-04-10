import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.io.IOException;


class MyDragGestureListener implements DragGestureListener, DragSourceListener {

	private DataFlavor draggableLabelFlavor = new DataFlavor(DraggableLabel.class, "DraggableLabel");
	private final DataFlavor[] okFlavors = { DataFlavor.stringFlavor, draggableLabelFlavor };

	@Override
	public void dragGestureRecognized(DragGestureEvent event) {
		DraggableLabel dLabel = (DraggableLabel) event.getComponent();

		Transferable transferable = new Transferable() {
			@Override
			public DataFlavor[] getTransferDataFlavors() {
				return okFlavors;
			}

			@Override
			public boolean isDataFlavorSupported(DataFlavor flavor) {
				for (DataFlavor supportFlavor : okFlavors) {
					if (supportFlavor.equals(flavor)) {
						return true;
					}
				}
				return false;
			}

			@Override
			public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
				System.out.println("In getTransferData");
				if (flavor.equals(draggableLabelFlavor)) {
					try {
						System.out.println("DnD: A clone is created");
						return dLabel.clone();
					} catch (CloneNotSupportedException e) {
					}
				} else if (flavor.equals(DataFlavor.stringFlavor)) {
					System.out.println("DnD: The text is returned");
					return dLabel.getText();
				}

				return null;

			}
		};

		new DragSource().startDrag(event, DragSource.DefaultCopyDrop, transferable, this);

	}

	@Override
	public void dragDropEnd(DragSourceDropEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragEnter(DragSourceDragEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragExit(DragSourceEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragOver(DragSourceDragEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dropActionChanged(DragSourceDragEvent arg0) {
		// TODO Auto-generated method stub

	}
}
