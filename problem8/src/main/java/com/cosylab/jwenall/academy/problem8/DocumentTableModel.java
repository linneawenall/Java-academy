package com.cosylab.jwenall.academy.problem8;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class DocumentTableModel extends AbstractTableModel {

	// Serial version UID.
	private static final long serialVersionUID = 2388066326503531750L;

	// Table columns' names
	private String[] columnNames = { "Project name", "Date", "Documentnumber", "Suffix" };

	// Data shown in the table.
	private ArrayList<Document> documents;

	public DocumentTableModel() {
		documents = new ArrayList<Document>();
		
	}

	@Override
	public int getRowCount() {
		return documents.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == 3) {
			return Integer.class;
		} else {
			// Otherwise
			return String.class;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return documents.get(rowIndex).getProjectName();
		case 1:
			return documents.get(rowIndex).getDate();

		case 2:
			return documents.get(rowIndex).getDocNumber();
		case 3:
			return documents.get(rowIndex).getSuffix();
		default:
			return null;
		}
	}

	public void setData(ArrayList<Document> documentFiles) {
		documents = documentFiles;
	}

	public ArrayList<Document> getData() {
		return documents;
	}

//	private class HeaderListener extends MouseAdapter {
//		protected DocumentTable table;
//
//		public HeaderListener(DocumentTable t) {
//			table = t;
//
//		}
//
//		public void createSorting(String[] args) throws IOException {
//			ConsolInput input = new ConsolInput(args);
//			Parsing parser = new Parsing(input.whatsInput());
//			parser.parse();
//			Sorter sorter = new Sorter();
//			sorter.sort(parser.getOrder(), parser.getList());
//		}
//
//		public void mouseClicked(MouseEvent e) {
//			if (e.getClickCount() == 2) {
//				System.out.println("Click recorded");
//				TableColumnModel colModel = table.getColumnModel();
//				int columnModelIndex = colModel.getColumnIndexAtX(e.getX());
//				int modelIndex = colModel.getColumn(columnModelIndex).getModelIndex();
//				if (modelIndex < 0) {
//					return;
//				}
//				// Path of selected document.
//				String path = table.getDocFiles().get(modelIndex).getPath();
//				String[] array = new String[1];
//				array[0] = path;
//				try {
//					createSorting(array);
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//
//				for (int i = 0; i < getColumnCount(); i++) {
//					TableColumn column = colModel.getColumn(i);
//					column.setHeaderValue(getColumnName(column.getModelIndex()));
//				}
//				table.getTableHeader().repaint();
//
//				// Collections.sort(vector,new MyComparator(isSortAsc));
//				table.tableChanged(new TableModelEvent(DocumentTableModel.this));
//				table.repaint();
//			}
//		}
//	}
}