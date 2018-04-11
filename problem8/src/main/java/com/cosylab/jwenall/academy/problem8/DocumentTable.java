package com.cosylab.jwenall.academy.problem8;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.regex.Pattern;

import javax.swing.JTable;
import javax.swing.SwingUtilities;

public class DocumentTable extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	DocumentTextArea docTextArea;

	// Regular expression for filtering files.
	private final  String FILTER_REGEX = "^(.+)-(\\d{4}-\\d{2}-\\d{2})-No(\\d{2})\\.(.+)$";
	private final Pattern PATTERN = Pattern.compile(FILTER_REGEX);

	// List of filtered documents.
	private ArrayList<Document> documentFiles = new ArrayList<>();

	public DocumentTable(DocumentTextArea docTextArea) {
		this.docTextArea = docTextArea;
		init();
	}

	private void init() {
		addMouseListener();
		setModel(new DocumentTableModel());

	}

	public void createDocuments(File directory) {

		// Matches file names with FILTER_REGEX.
		Matcher matcher;

		// Check if 'directory' is directory.
		if (directory.isDirectory()) {
			for (File documentFile : directory.listFiles()) {
				matcher = PATTERN.matcher(documentFile.getName());

				if (matcher.matches()) {
					// Project name.
					String projectName = matcher.group(1);

					String date = matcher.group(2);
					if (!isValidDate(date)) {
						System.out.println("ERROR: Date invalid for file: " + documentFile.getAbsolutePath());
						date = null;
					}

					// Document's number.
					int documentNumber = Integer.parseInt(matcher.group(3));

					// Document's suffix.
					String suffix = matcher.group(4);

					// Document's path.
					String path = documentFile.getAbsolutePath();

					// Create new 'Document' object and add it to ArrayList.
					if ((date != null) && (suffix != null) && (projectName != null)) {
						documentFiles.add(new Document(projectName, date, documentNumber, suffix, path));
					} else {
						docTextArea.append("\n ERROR: Date invalid for file:" + documentFile.getAbsolutePath());
					}
				}
			}
		}
	}

	private void showText(final int documentIndex) {

		// Show content only if listener is assigned.
		if (docTextArea != null) {
			// Render text in other thread.
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					try {

						// Path of selected document.
						String path = documentFiles.get(documentIndex).getPath();
						BufferedReader input = new BufferedReader(new FileReader(path));

						// Clean text area before adding text.
						docTextArea.clean();

						// Read line by line.
						try {
							String line = null;
							while ((line = input.readLine()) != null) {
								docTextArea.update(line);
							}
						} finally {
							input.close();
						}
					} catch (IOException ex) {
						System.out.println(ex.getMessage());
					}
				}
			});
		}
	}

	private void addMouseListener() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (e.getClickCount() == 2) {
					DocumentTable tempTable = (DocumentTable) e.getSource();

					int documentIndex = tempTable.getSelectedRow();
					int modelIndex = convertRowIndexToModel(documentIndex);
					if (documentIndex != modelIndex) {
						documentIndex = modelIndex;
					}
					showText(documentIndex);
				}
			}
		});
	}

	public void putFilesToDataModel() {
		((DocumentTableModel) getModel()).setData(documentFiles);
	}

	public void updateTable() {
		((DocumentTableModel) getModel()).fireTableDataChanged();
	}

	public static boolean isValidDate(String inDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		try {
			dateFormat.parse(inDate.trim());
		} catch (ParseException pe) {
			return false;
		}
		return true;
	}

	public List getdocFiles() {
		return documentFiles;
	}

	public void clearView() {
		documentFiles.clear();
		docTextArea.clean();

	}

}
