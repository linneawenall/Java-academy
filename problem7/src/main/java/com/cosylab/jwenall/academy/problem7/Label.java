package com.cosylab.jwenall.academy.problem7;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.TransferHandler;

public class Label extends JPanel {
	private JLabel label;

	public Label() {
		label = new JLabel("Drag me to panel or textField");
		add(Box.createHorizontalStrut(35));
		 
		label.setTransferHandler(new TransferHandler(""));
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent mEvt) {
				JComponent component = (JComponent) mEvt.getSource();
				TransferHandler tHandler = component.getTransferHandler();
				tHandler.exportAsDrag(component, mEvt, TransferHandler.COPY);
			}
		});
	}
}
