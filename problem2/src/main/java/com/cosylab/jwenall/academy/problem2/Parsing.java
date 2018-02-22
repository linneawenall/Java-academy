package com.cosylab.jwenall.academy.problem2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Parsing {
	private int order;
	private ArrayList<Number> list;
	private String input;
	String [] inArray;

	public Parsing(String input) throws FileNotFoundException {
		this.input = input;
		list = new ArrayList<Number>();
		System.out.println("Parsing object created");

	}

	public void parse() {
		this.inArray = input.split("[,\\s]+");
		System.out.println("String [] inArray: " + Arrays.toString(inArray));
		addNumbers(inArray);
		System.out.println("List.toString() in parse(): " + list.toString());

	}

	public int whichOrder(String sortOrder) {
		System.out.println("Executing sortOrder method for string: " + sortOrder);
		if (sortOrder.equals("A:")) {
			order = 1;
		} else if (sortOrder.equals("D:")) {
			order = -1;
		} else {
			order = 0;
		}
		System.out.println("Order set to: " + order);
		return order;
	}

	public void addNumbers(String[] inputs) throws IllegalArgumentException, NullPointerException {
		inArray = inputs;
		System.out.println("String [] inputs to addNumber: " + Arrays.toString(inputs));
		if (inputs == null) {
			throw new NullPointerException("ERROR: String array is null");
		}
		for (int i = 0; i < inputs.length; i++) {
			if ((i == 0)) {
				System.out.println("Executing for i == 0 in addNumber");
				order = whichOrder(inputs[0]);
			} else if (isInteger(inputs[i])) {
				System.out.println("addNumbermethod " + inputs[i]);
				list.add(Integer.parseInt(inputs[i]));
			} else if (isDouble(inputs[i])) {
				System.out.println("addNumbermethod " + inputs[i]);
				list.add(Double.parseDouble(inputs[i]));
			} else {
				throw new IllegalArgumentException("Only numbers and the letters A and D can be read");
			}
		}
	}

	public boolean isInteger(String number) throws NumberFormatException {
		try {
			Integer.parseInt(number);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public boolean isDouble(String number) throws NumberFormatException {
		try {
			Double.parseDouble(number);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public ArrayList<Number> getList() {
		return list;
	}

	public int getOrder() {
		return order;
	}
	
	public String[] getStringArray(){
		return inArray;
	}

}
