package com.cosylab.jwenall.academy.problem2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/*Comparator<Number> defines the object type that overrides compare method in comparator class*/
public class SortingAlgorithm implements Comparator<Number> {
	private ArrayList<Number> list;
	private String order; // might wanna change to int
	private File file;

	public SortingAlgorithm() {
		list = new ArrayList<Number>();
		this.order = "A";
	}

	/* Uses Collections to sort */
	public void sortList() {
		if (!list.isEmpty()) {
			Collections.sort(list, this);
		} else
			throw new IllegalArgumentException("ERROR: List is empty");
	}

	/* Overrides compare method in Comparator. */
	@Override
	public int compare(Number n1, Number n2) {
		if (n1.doubleValue() < n2.doubleValue()) {
			return 1;
		}
		if (n1.doubleValue() > n2.doubleValue()) {
			return -1;
		}
		return 0;
	}
	/* Reads in the file written in the console*/

	public void addInput(String fileName) throws FileNotFoundException {
		FileReader fr = new FileReader(fileName);
		Scanner input = new Scanner(fr);
		String in = input.nextLine();
		input.close();

	}

	public void whichOrder(String order) { // tries which input from A and D

	}

	/*
	 * creates a file from the input the user puts in. this makes it possible to
	 * call the same addInput method
	 * 
	 */
	public void createFile() throws IOException {
		file = new File("Numbers");
		PrintWriter output = new PrintWriter(file);
		int ch;
		while ((ch = System.in.read()) != '\n') {
			output.println((char) ch);
		}
		output.close();
	}

	public boolean isInteger(String number) {
		try {
			Integer.parseInt(
					number);/*
							 * Returns a new int initialized to the value
							 * represented by the specified String, as performed
							 * by the valueOf method of class Double.
							 */
		} catch (NumberFormatException e) { /*
											 * Thrown to indicate that the
											 * application has attempted to
											 * convert a string to one of the
											 * numeric types, but that the
											 * string does not have the
											 * appropriate format.
											 */
			return false;
		}
		return true;
	}

	public boolean isDouble(String number) {
		try {
			Double.parseDouble(
					number);/*
							 * Returns a new double initialized to the value
							 * represented by the specified String, as performed
							 * by the valueOf method of class Double.
							 */
		} catch (NumberFormatException e) { /*
											 * Thrown to indicate that the
											 * application has attempted to
											 * convert a string to one of the
											 * numeric types, but that the
											 * string does not have the
											 * appropriate format.
											 */
			return false;
		}
		return true;
	}
}
