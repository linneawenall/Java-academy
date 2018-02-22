package com.cosylab.jwenall.academy.problem2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

// REVIEW (low): you can probably remove this file, since you have the "ParsingTest" unit test.
//Try to connect parsing and sorter so everything works. Rewrite tests
public class ParsingSorterMain {

	public static void main(String[] args) throws FileNotFoundException {
		String test = new String("A: 1,8,5,7");
		ConsolInput input = new ConsolInput(test);
		//Parsing parser = new Parsing(input.whatsInput());
		//Parsing parser = new Parsing("A: 1,8,5,7");
		Parsing parser = new Parsing(new ConsolInput("").whatsInput());
		parser.parse();
		Sorter sorter = new Sorter();
		sorter.sort(parser.getOrder(), parser.getList());
		
		int listLength = parser.getList().size();
		if (parser.getOrder() == 1)
			System.out.print("Ascending: ");
		else if (parser.getOrder() == -1)
			System.out.print("Descending: ");
		else
			System.out.print("Unsorted: ");

		for (int i = 0; i < listLength; i++) {
			System.out.print(parser.getList().get(i));
			if (i != listLength - 1)
				System.out.print(", ");
		}
		System.out.println();
	
		
	}

}
