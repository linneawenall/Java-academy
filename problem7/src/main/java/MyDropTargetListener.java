import java.awt.Component;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;

import javax.swing.JPanel;


class MyDropTargetListener extends DropTargetAdapter {
	private DataFlavor draggableLabelFlavor = new DataFlavor(DraggableLabel.class, "DraggableLabel");

	private DropTarget dropTarget;
	private JPanel p;

	public MyDropTargetListener(JPanel panel) {
		p = panel;
		this.dropTarget = new DropTarget(panel, DnDConstants.ACTION_COPY, this, true, null);

	}

	@Override
	public void drop(DropTargetDropEvent event) {
		try {
			DropTarget test = (DropTarget) event.getSource();
			Component ca = (Component) test.getComponent();
			Point dropPoint = ca.getMousePosition();
			Transferable transferable = event.getTransferable();
//			System.out.println(event.isDataFlavorSupported(draggableLabelFlavor));
//			System.out.println(draggableLabelFlavor.toString());
			if (event.isDataFlavorSupported(draggableLabelFlavor)) {
//				System.out.println("in if");
				DraggableLabel dragContents = (DraggableLabel) transferable.getTransferData(draggableLabelFlavor);
//				System.out.println("After dragcontents");
				if (dragContents != null) {
					dragContents.setLocation(dropPoint);
					p.add(dragContents);
					p.revalidate();
					p.repaint();
					event.dropComplete(true);

				}
			} else {
				event.rejectDrop();
			}
		} catch (Exception e) {
			event.rejectDrop();
		}
	}
}