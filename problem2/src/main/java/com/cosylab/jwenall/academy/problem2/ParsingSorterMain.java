package com.cosylab.jwenall.academy.problem2;

import java.io.FileNotFoundException;

public class ParsingSorterMain {

	public static void main(String[] args) throws FileNotFoundException {
		Parsing parser = new Parsing("");
		Sorter sorter = new Sorter(parser.getOrder(), parser.getArray());

	}

}
