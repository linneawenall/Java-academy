package com.cosylab.jwenall.academy.problem2;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Sorter implements Comparator<Number> { // Main class will manage
													// everything.
	private int order;
	private ArrayList<Number> list;

	public Sorter(int order, String[] input) { // takes the output from Parsing
												// class. Optional: input class
		list = new ArrayList<Number>();
		this.order = order;
		System.out.println("SorterOrder: " + order);
		addNumber(input);
		sortList();
	}
	
	/* Uses Collections to sort */
	public void sortList() throws IllegalArgumentException {
		if (!list.isEmpty()) {
			Collections.sort(list, this);
			printList();
		} else
			throw new IllegalArgumentException("ERROR: List is empty");
	}

	public void addNumber(String[] numbers)throws IllegalArgumentException, NullPointerException {
		if(numbers == null){
			throw new NullPointerException("ERROR: String array is null");
		}
		for (int i = 1; i < numbers.length; i++) {
			if (isInteger(numbers[i])) {
				System.out.println("addNumbermethod " +numbers[i]);
				list.add(Integer.parseInt(numbers[i]));
			} else if (isDouble(numbers[i])) {
				System.out.println("addNumbermethod " +numbers[i]);
				list.add(Double.parseDouble(numbers[i]));
			} else {
				throw new IllegalArgumentException("Only numbers and the letters A and D can be read");
			}
		}
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

	public boolean isInteger(String number)throws NumberFormatException {
		try {
			Integer.parseInt(number);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public boolean isDouble(String number) throws NumberFormatException{
		try {
			Double.parseDouble(number);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public void printList() { //this is just for visualising the printout
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