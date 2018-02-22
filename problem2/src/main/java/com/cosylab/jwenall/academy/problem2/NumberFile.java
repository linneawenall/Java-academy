package com.cosylab.jwenall.academy.problem2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

// REVIEW (low): you can probably remove this file. It's not used anywhere, right?
public class NumberFile {

	public static void main(String[] args) {
		File file = new File("Numbers");

		try { //creates file
			PrintWriter output = new PrintWriter(file);
			output.println("A");
			output.println("1");
			output.println("2");
			output.println("3");
			output.close();
		} catch (IOException ex) {
			System.out.printf("Error: %s\n", ex);

		}
		try { //reads the file
			Scanner input = new Scanner(file);
			String order = input.nextLine();
			int n1 = input.nextInt();
			int n2 = input.nextInt();
			int n3 = input.nextInt();
			System.out.printf("Order: %s Numbers: %s %s %s \n", order, n1, n2, n3);
		} catch (FileNotFoundException ex) {
			System.out.printf("Error: %s\n", ex);

		}

	}

}
