package com.cosylab.jwenall.academy.problem2;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;

import org.junit.After;
import org.junit.Before;

public class ParsingTest {
	Parsing p;

	@Before
	public void setUp() throws Exception {
		p = new Parsing("");
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testConsolInput() throws FileNotFoundException {
		try {
			p.consolInput(p.getString());
		} catch (FileNotFoundException exception) {
			fail("ERROR: file not found: " + exception);
		}
		try {
			p.consolInput("");
		} catch (FileNotFoundException exception) {
			fail("ERROR: not valid empty input: " + exception);
		}
	}

	@Test
	public void testWhichOrder() {
		int order = p.whichOrder(p.getArray()[0]);
		assertTrue("Order is 1", p.getOrder() == 1);
	}
}
