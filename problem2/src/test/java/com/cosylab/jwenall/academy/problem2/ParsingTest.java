package com.cosylab.jwenall.academy.problem2;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


import java.io.FileNotFoundException;

import org.junit.After;
import org.junit.Before;

public class ParsingTest {
	Parsing p;

	@Before
	public void setUp() throws Exception {
		p = new Parsing("A: 1,8,5,7,2.5,6.7,1.0");
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testParse() throws FileNotFoundException {
			p.parse();
			Number five = p.getList().get(2);
			assertEquals("The List contains numbers", five, 5);
}}