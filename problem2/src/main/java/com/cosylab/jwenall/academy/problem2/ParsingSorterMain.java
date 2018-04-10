package com.cosylab.jwenall.academy.problem2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class ParsingSorterMain {

	public static void main(String[] args) throws IOException {
		System.out.println(Arrays.toString(args));
		ConsolInput input = new ConsolInput(args);
		Parsing parser = new Parsing(input.whatsInput());
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
