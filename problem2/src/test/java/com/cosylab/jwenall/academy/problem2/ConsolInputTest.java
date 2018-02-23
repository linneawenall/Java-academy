package com.cosylab.jwenall.academy.problem2;

import org.junit.Test;

import static org.junit.Assert.*;

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
	ConsolInput c;
	File file;
	String[] stringArray;

	@Before
	public void setUp() throws Exception {
		test = new String("A: 1,8,5,7,2.5,6.7,1.0");

	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testConsolInputString() throws FileNotFoundException {
		stringArray = new String[test.length()];
		stringArray = test.split("[,\\s]+");
		c = new ConsolInput(stringArray);
		assertTrue("String input starts with A", c.whatsInput().startsWith("A"));

	}

	@Test
	public void testConsolInputEmpty() throws FileNotFoundException, NullPointerException {
		stringArray = new String[0];
		System.out.println("Write 'A: 1,2,9,3.0'");
		c = new ConsolInput(stringArray);
		assertTrue("String input starts with A", c.whatsInput().startsWith("A"));

	}

	/* This test fails. Still don't understandd how to find the file */
	@Test
	public void testConsolInputFile() throws FileNotFoundException {
		stringArray = new String[1];
		stringArray[0] = "file";
		c = new ConsolInput(stringArray);
		assertTrue("String input starts with A", c.whatsInput().startsWith("A"));
	}
}
