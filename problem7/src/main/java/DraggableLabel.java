import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragSource;

import javax.swing.JLabel;


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
