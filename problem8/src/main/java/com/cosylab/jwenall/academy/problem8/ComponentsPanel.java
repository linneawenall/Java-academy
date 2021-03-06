package com.cosylab.jwenall.academy.problem8;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.ParseException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableRowSorter;


public class ComponentsPanel extends JPanel implements ActionListener {

	// Serial version UID
	private static final long serialVersionUID = 7966690292690174755L;

	// Components
	private DocumentTable documentTable;
	private JScrollPane textAreaScrollPane, tableScrollPane;
	private JButton openButton;
	private DocumentTextArea documentTextArea;
	private JFileChooser folderChooser;
	private JTextField statusText;

	public ComponentsPanel() {
		// Call super constructor.
		super();

		// Initialize components and their options.
		initialize();

		// Set layout of the content pane.
		setLayout();
	}

	private void initialize() {

		// Create components.
		tableScrollPane = new JScrollPane();
		textAreaScrollPane = new JScrollPane();
		openButton = new JButton("Open folder");
		documentTextArea = new DocumentTextArea();
		documentTable = new DocumentTable(documentTextArea);
		folderChooser = new JFileChooser(".");
		
		DocumentTableModel docModel = new DocumentTableModel();
		


	    documentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    
	    
	    documentTable.setModel(docModel);
	    TableRowSorter sort = new TableRowSorter<DocumentTableModel>(docModel);
	    documentTable.setRowSorter(sort);

		 
	        //When selection changes, provide user with row numbers for
	        //both view and model.
//	        documentTable.getSelectionModel().addListSelectionListener(
//	                new ListSelectionListener() {
//	                    public void valueChanged(ListSelectionEvent event) {
//	                        int viewRow = documentTable.getSelectedRow();
//	                        if (viewRow < 0) {
//	                            System.out.println("Pressed header.");
//	                        } else {
//	                            int modelRow = 
//	                                documentTable.convertRowIndexToModel(viewRow);
//	                            System.out.println(
//	                                String.format("Selected Row in view: %d. " +
//	                                    "Selected Row in model: %d.", 
//	                                    viewRow, modelRow));
//	                        }
//	                    }
//	                }
//	        );

	        
		
		

		// File chooser can choose directories only
		folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		// View point of scroll panes.
		tableScrollPane.setViewportView(documentTable);
		textAreaScrollPane.setViewportView(documentTextArea);

		// Add listeners.
		// documentTable.addTextAreaListener(documentTextArea);
		openButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		int status = folderChooser.showOpenDialog(null);

		if (status == JFileChooser.APPROVE_OPTION) {
			try {
				operateWithTable(folderChooser.getSelectedFile());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void operateWithTable(File directory) throws ParseException {
		// Clear previous files if any
		 documentTable.clearView();

		// Create showing files
		documentTable.createDocuments(directory);

		// Put files to table's data model
		documentTable.putFilesToDataModel();

		// Update table's data
		documentTable.updateTable();
	}

	private void setLayout() {
		// Set border.
		setBorder(new EmptyBorder(5, 5, 5, 5));

		GroupLayout groupLayoutPane = new GroupLayout(this);
		groupLayoutPane.setHorizontalGroup(groupLayoutPane.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayoutPane.createSequentialGroup().addContainerGap()
						.addGroup(groupLayoutPane.createParallelGroup(Alignment.LEADING)
								.addComponent(textAreaScrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 669,
										Short.MAX_VALUE)
								.addComponent(tableScrollPane, GroupLayout.DEFAULT_SIZE, 669, Short.MAX_VALUE)
								.addComponent(openButton))
						.addContainerGap()));
		groupLayoutPane.setVerticalGroup(groupLayoutPane.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayoutPane.createSequentialGroup().addContainerGap().addComponent(openButton).addGap(18)
						.addComponent(tableScrollPane, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
						.addGap(35).addComponent(textAreaScrollPane, GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
						.addContainerGap()));

		// Set the group layout.
		setLayout(groupLayoutPane);
	}
}
