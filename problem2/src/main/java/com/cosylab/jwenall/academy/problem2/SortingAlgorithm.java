package com.cosylab.jwenall.academy.problem2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/*Comparator<Number> defines the object type that overrides compare method in comparator class*/
// REVIEW (low): you probably don't need this class anymore, right?
public class SortingAlgorithm implements Comparator<Number> {
	private ArrayList<Number> list;
	private int order;
	private File file;

	public SortingAlgorithm() {
		list = new ArrayList<Number>();
		this.order = 0;
	}

	/* Uses Collections to sort */
	public void sortList() {
		if (!list.isEmpty()) {
			Collections.sort(list, this);
		} else
			throw new IllegalArgumentException("ERROR: List is empty");
	}

	/*
	 * @param the array of strings to add to the list
	 */
	public void addNumber(String[] numbers) {
		for (int i = 0; i < numbers.length; i++) {
			if (i == 0) {
				whichOrder(numbers[0]);
				System.out.println("Order: " +order);
			}
			else if (isInteger(numbers[i])) {
				System.out.println(numbers[i]);
				list.add(Integer.parseInt(numbers[i]));
			} else if (isDouble(numbers[i])) {
				System.out.println(numbers[i]);
				list.add(Double.parseDouble(numbers[i]));
			} else {
				throw new IllegalArgumentException("Only numbers and the letters A and D can be read");
			}
		}
	}

	/* Reads in the file written in the console or from computer */

	public void consolInput(String fileName) throws FileNotFoundException {
		Scanner input;
		if (fileName.isEmpty()) {
			input = new Scanner(System.in);
		} else {
			FileReader fr = new FileReader(fileName);
			input = new Scanner(fr);
		}
		String in = input.nextLine();
		input.close();
		String[] inArray = in.split("[,\\s]+"); //could have replaceAll("^:[,\\s]+", "") after in.
		System.out.println(Arrays.toString(inArray));
		readInput(inArray);

	}

	public void readInput(String[] input) throws FileNotFoundException {
		if (input.length == 0) { // if empty
			consolInput("");
		}
		if (input.length == 1) { // something if its a file
			consolInput(input[0]);
		} else {
			addNumber(input);
		}
	}

	public int whichOrder(String sortOrder) { 
		System.out.println(sortOrder);
		
		if (sortOrder.equals("A:")) {
			order = 1;
		}
		else if (sortOrder.equals("D:")) {
			order = -1;
		} else {
			order = 0;
		}
		return order;
	}

	public boolean isInteger(String number) {
		try {
			Integer.parseInt(number);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public boolean isDouble(String number) {
		try {
			Double.parseDouble(number);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/* Overrides compare method in Comparator. */
	@Override
	public int compare(Number n1, Number n2) {
		if (n1.doubleValue() < n2.doubleValue()) {
			return order * -1;
		}
		if (n1.doubleValue() > n2.doubleValue()) {
			return order * 1;
		}
		return 0;
	}

	public void printList() {
		int listLength = list.size();
		if (order == 1)
			System.out.print("Ascending: ");
		else if (order == -1)
			System.out.print("Descending: ");
		else
			System.out.print("Unsorted: ");

		for (int i = 0; i < listLength; i++) {
			System.out.print(list.get(i));
			if (i != listLength - 1)
				System.out.print(", ");
		}
		System.out.println();
	}

}
