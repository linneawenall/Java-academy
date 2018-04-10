package com.cosylab.jwenall.academy.problem8;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class DocumentTable extends JTable {

	DocumentTextArea docTextArea;

	// Regular expression for filtering files.
	private final static String FILTER_REGEX = "^(.+)-(\\d{4}-\\d{2}-\\d{2})-No(\\d{2})\\.(.+)$";
	private final static Pattern PATTERN = Pattern.compile(FILTER_REGEX);

	// List of filtered documents.
	private ArrayList<Document> documentFiles = new ArrayList<Document>();

	public DocumentTable(DocumentTextArea docTextArea) {
		this.docTextArea = docTextArea;
		init();
	}

	private void init() {
		addMouseListener();
		setModel(new DocumentTableModel());

	}

	public void createDocuments(File directory) throws ParseException {

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
						date = "Invalid date";
					}
					System.out.println(date);

					// Document's number.
					int documentNumber = Integer.parseInt(matcher.group(3));

					// Document's suffix.
					String suffix = matcher.group(4);

					// Document's path.
					String path = documentFile.getAbsolutePath();

					// Create new 'Document' object and add it to ArrayList.
					documentFiles.add(new Document(projectName, date, documentNumber, suffix, path));
				}
			}
		}
	}

	private void showText(final int documentIndex) {

		// Show content only if listener is assigned.
		if (docTextArea != null) {
			System.out.println("In if in showText");
			// Render text in other thread.
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					try {
						// Path of selected document.
						String path = documentFiles.get(documentIndex).getPath();
						System.out.println(path);
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
					System.out.println("Click recorded");
					DocumentTable tempTable = (DocumentTable) e.getSource();

					int documentIndex = tempTable.getSelectedRow();
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

	public ArrayList<Document> getDocFiles() {
		return documentFiles;
	}

}
