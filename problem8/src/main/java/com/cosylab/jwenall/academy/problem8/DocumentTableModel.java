package com.cosylab.jwenall.academy.problem8;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

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
}