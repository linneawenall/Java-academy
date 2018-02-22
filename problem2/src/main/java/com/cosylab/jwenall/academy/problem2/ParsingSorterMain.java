package com.cosylab.jwenall.academy.problem2;

import java.io.FileNotFoundException;

public class ParsingSorterMain {

	public static void main(String[] args) throws FileNotFoundException {
		String test = new String("A: 1,8,5,7");
		Parsing parser = new Parsing("test");
		Sorter sorter = new Sorter(parser.getOrder(), parser.getArray());

	}

}
