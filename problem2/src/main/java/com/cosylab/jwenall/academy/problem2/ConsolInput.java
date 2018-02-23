package com.cosylab.jwenall.academy.problem2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class ConsolInput {
	private String[] input;
	private String inTo;

	public ConsolInput(String [] input) throws FileNotFoundException {
		consolInput(input);
	}

	public void consolInput(String[] input) throws FileNotFoundException { //reDo consolinput based on the length of the args array. if lenght == 0, scanner(system.in), if lenght == 1, filereader, if lenght >1 should handle string
		if (input == null) {
			throw new NullPointerException("Error: input is null");
		}
		Scanner in;
		if (input.length == 0) {
			in = new Scanner(System.in);
		} else if (input.length== 1) { 
			System.out.println("Reads file");
			File file = new File(input[0]);
			in = new Scanner(file);
		} else { 
			System.out.println("Reaching input in consolInput");
			String inputToString = "";
			for (int i = 0; i < input.length; i++) 
				inputToString = inputToString + input[i]+" ";	
			in = new Scanner(inputToString); 
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
