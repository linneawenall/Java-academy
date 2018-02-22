package com.cosylab.jwenall.academy.problem2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class ConsolInput {
	private String input;
	private String inTo;

	public ConsolInput(String input) throws FileNotFoundException {
		consolInput(input);
	}

	public void consolInput(String input) throws FileNotFoundException {
		if (input == null) {
			throw new NullPointerException("Error: input is null");
		}
		Scanner in;
		if (input.isEmpty()) {
			in = new Scanner(System.in);
		} else if (!input.isEmpty()) { //if string w numbers is input
			in = new Scanner(input);
		} else { // if file is input. Needs fixing. Doesnt reach this
			System.out.println("else in consolInput");
			FileReader fr = new FileReader(input);
			in = new Scanner(fr);
		}
		String inTo = in.nextLine();
		this.inTo = inTo;
		in.close();
	}

	public String whatsInput() {
		System.out.println("whatsInput: " + inTo);
		return inTo;
	}
}
