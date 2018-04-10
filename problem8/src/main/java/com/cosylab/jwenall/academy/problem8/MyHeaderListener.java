package com.cosylab.jwenall.academy.problem8;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.event.TableModelEvent;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class MyHeaderListener extends MouseAdapter {
	protected DocumentTable table;
	protected DocumentTableModel model;

	public MyHeaderListener(DocumentTable t, DocumentTableModel m) {
		table = t;
		model = m;

	}

	public void createSorting(String[] args) throws IOException {
		ConsolInput input = new ConsolInput(args);
		Parsing parser = new Parsing(input.whatsInput());
		parser.parse();
		Sorter sorter = new Sorter();
		sorter.sort(parser.getOrder(), parser.getList());
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			System.out.println("Click recorded");
			TableColumnModel colModel = table.getColumnModel();
			int columnModelIndex = colModel.getColumnIndexAtX(e.getX());
			System.out.println("columnModelIndex: " + columnModelIndex);
			int modelIndex = colModel.getColumn(columnModelIndex).getModelIndex();
			System.out.println("modelIndex: " + modelIndex);
			if (modelIndex < 0) {
				return;
			}
			// Path of selected document. This only gives the top document.
			
			String path = table.getDocFiles().get(modelIndex).getPath();
			String[] array = new String[1];
			array[0] = path;
			try {
				// What I want to send into createSorting(array) is an array containing all the
				// content from every row in the specific column that has been clicked. So not
				// what's below
				createSorting(array);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			for (int i = 0; i < model.getColumnCount(); i++) {
				TableColumn column = colModel.getColumn(i);
				column.setHeaderValue(model.getColumnName(column.getModelIndex()));
			}
			table.getTableHeader().repaint();
			table.tableChanged(new TableModelEvent(model));
			table.repaint();
		}
	}
}