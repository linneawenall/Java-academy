import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

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
