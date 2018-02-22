package com.cosylab.jwenall.academy.problem2;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;

public class ConsolInputTest {
	String test;
	String fileName;
	Sorter s;
	Parsing p;
	ConsolInput c;
	File file;

	@Before
	public void setUp() throws Exception {
		test = new String("A: 1,8,5,7,2.5,6.7,1.0");
	
		
		
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testConsolInputString() throws FileNotFoundException {
		c = new ConsolInput(test);
		assertTrue("String input is same as output", test.equals(c.whatsInput()));

	}

	@Test
	public void testConsolInputEmpty() throws FileNotFoundException, NullPointerException {
		System.out.println("Write 'A: 1,2,9,3.0'");
		c = new ConsolInput("");
		assertTrue("String input starts with A", c.whatsInput().startsWith("A"));

	}

	@Test
	public void testConsolInputFile() throws FileNotFoundException {
		file = new File("file.txt");
		Scanner input = new Scanner(file);
		String inTo = input.nextLine();
		c = new ConsolInput(inTo);
		p = new Parsing(c.whatsInput());
		p.parse();
		Sorter sorter = new Sorter();
		sorter.sort(p.getOrder(), p.getList());
		System.out.println("sorted list: " +s.getSortedList().toString() );
		assertTrue("Number at third index is 2.5",(double) s.getSortedList().get(2)==2.5 );
	}
}
