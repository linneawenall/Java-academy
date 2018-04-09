import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.TextField;
import java.awt.datatransfer.DataFlavor;
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
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.border.TitledBorder;

public class DnD {


	public static void main(String[] args) {
		createAndShowJFrame();
	}

	public static void createAndShowJFrame() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {

				JFrame frame = createJFrame();
				frame.setVisible(true);

			}
		});
	}

	private static JFrame createJFrame() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.setTitle("Drag and Drop Simulation");

		JPanel panel = createEmptyJPanel();
		new MyDropTargetListener(panel);// this must be done or we wont be able to drop any image onto the empty panel

		frame.add(panel, BorderLayout.CENTER);

		try {
			frame.add(createDragLabelPanel(), BorderLayout.SOUTH);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		frame.add(createTextField(), BorderLayout.NORTH);

		frame.pack();

		return frame;
	}

	private static JPanel createEmptyJPanel() {
		
		final JPanel p = new JPanel() {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(300, 300);
			}
		};
		p.setLayout(null);
		p.setBorder(new TitledBorder("Drag label onto panel below or textField above"));

		TransferHandler dnd = new TransferHandler() {
			private DataFlavor draggableLabelFlavor = new DataFlavor(DraggableLabel.class, "DraggableLabel");
			@Override
			public boolean canImport(TransferSupport support) {
				if (!support.isDrop()) {
					System.out.println("Can't drop in canImport");
					return false;
				}

				if (!support.isDataFlavorSupported(draggableLabelFlavor)) {

					return false;
				}
				System.out.println("Can Import");
				return true;
			}

			@Override
			public boolean importData(TransferSupport support) {
				if (!canImport(support)) {
					return false;
				}

				Transferable tansferable = support.getTransferable();
				DraggableLabel draggableLabel = null;
				try {
					draggableLabel = (DraggableLabel) tansferable.getTransferData(draggableLabelFlavor);
				} catch (Exception e) {
					return false;
				}
				draggableLabel.setLocation(support.getDropLocation().getDropPoint().x,
						support.getDropLocation().getDropPoint().y);
				p.add(draggableLabel);
				return true;
			}
		};
		
		p.setTransferHandler(dnd);

		return p;
	}

	private static JPanel createDragLabelPanel() throws Exception {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder("Drag Label to Panel or TextField"));

		DraggableLabel dLabel = new DraggableLabel("Hello");
		dLabel.setForeground(Color.BLUE);

		// MyDragGestureListener dlistener = new MyDragGestureListener();
		// DragSource ds1 = new DragSource();
		// ds1.createDefaultDragGestureRecognizer(dLabel, DnDConstants.ACTION_COPY,
		// dlistener);

		panel.add(dLabel);

		return panel;
	}


	private static JTextField createTextField() {
		JTextField textField = new JTextField() {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(30, 30);
			}
		};
		textField.setBorder(new TitledBorder("Drop Label here"));
		textField.setDragEnabled(true);
		textField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		return textField;
	}
}

class DraggableLabel extends JLabel implements Cloneable {
	DragSource dragSource;

	public DraggableLabel(String text) {

		setText(text);
		dragSource = new DragSource();
		MyDragGestureListener listener = new MyDragGestureListener();

		dragSource.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY_OR_MOVE, listener);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}

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

class ThisTransferHandler extends TransferHandler {

}

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
