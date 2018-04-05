package com.cosylab.jwenall.academy.problem7;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.TransferHandler;

public class DragAndDrop extends JPanel implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel label;
	private JTextField textField;

	// public DragAndDrop() {
	//
	// }

	public void placeComponents(JPanel panel) {
		panel.add(DraggableLabel());
		panel.add(DroppableTextField());
	}

	public JLabel DraggableLabel() {
		label = new JLabel("Hey - I'm a label. Drag me somewhere!", SwingConstants.LEADING);
		label.setOpaque(true);
		label.setBackground(Color.decode("#CCCCCC"));
		label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		label.setBounds(10, 100, 120, 25);

		label.setTransferHandler(new TransferHandler("text"));

		MouseListener listener = new DragMouseAdapter();

		label.addMouseListener(listener);
		return label;
	}

	public JTextField DroppableTextField() {
		textField = new JTextField(40);
		textField.setDragEnabled(true);
		textField = new JTextField(20);
		textField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		textField.setBounds(10, 140, 120, 25);
		return textField;
	}

	private class DragMouseAdapter extends MouseAdapter {
		public void mousePressed(MouseEvent mEvt) {
			JComponent comp = (JComponent) mEvt.getSource();
			TransferHandler handler = comp.getTransferHandler();
			handler.exportAsDrag(comp, mEvt, TransferHandler.COPY);
		}
	}

	/*
	 * Creates and returns a copy of this object. The precise meaning of "copy"
	 * may depend on the class of the object. The general intent is that, for
	 * any object x, the expression: 1) x.clone() != x will be true 2)
	 * x.clone().getClass() == x.getClass() will be true, but these are not
	 * absolute requirements. 3) x.clone().equals(x) will be true, this is not
	 * an absolute requirement.
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
