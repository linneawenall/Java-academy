package com.cosylab.jwenall.academy.problem2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

public class Parsing { // will create the String []number and int order
	private int order;
	String[] numberArray;
	private String number;

	/* @param String with the number information, works for all three inputs. */
	public Parsing(String number) throws FileNotFoundException {
		this.number = number;
		numberArray = consolInput(number);
		order = whichOrder(numberArray[0]);
	}

	/*
	 * @param the array of strings to add to the list
	 */

	/* Reads in the file written in the console or from computer */

	public String[] consolInput(String fileName) throws FileNotFoundException {
		Scanner input;
		if (fileName.isEmpty()) {
			input = new Scanner(System.in);
		} else if (!fileName.isEmpty()) {
			input = new Scanner(fileName);
			//Here I need something that reads whats in the string
		} else {
			System.out.println("else in consolInput");
			FileReader fr = new FileReader(fileName);
			input = new Scanner(fr);
		}
		String in = input.nextLine();
		input.close();
		String[] inArray = in.split("[,\\s]+");
		System.out.println(Arrays.toString(inArray));
		return inArray;
	}

	public int whichOrder(String sortOrder) {
		System.out.println(sortOrder);

		if (sortOrder.equals("A:")) {
			order = 1;
		} else if (sortOrder.equals("D:")) {
			order = -1;
		} else {
			order = 0;
		}
		return order;
	}

	public String[] getArray() {
		return numberArray;
	}

	public int getOrder() {
		return order;
	}

	public String getString() { // Only really need this one in ParsingTest
		return number;
	}

}
