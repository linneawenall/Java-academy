package com.cosylab.jwenall.academy.problem2;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
			
	}

	@Test
	public void testWhichOrder() {
		p.parse();
		int order = p.whichOrder(p.getStringArray()[0]);
		assertTrue("Order is 1", p.getOrder() == 1);
	}
	
	@Test
	public void testAddNumber() throws IllegalArgumentException, NullPointerException {
		p.parse();
		p.addNumbers(p.getStringArray());
		int seven = p.getList().get(3).intValue();
		assertEquals("Value at 3rd index is 7", seven, 7);
	}
	@Test
	public void testIsInteger(){
		p.parse();
		try {
			p.isInteger(p.getStringArray()[3]);
			
		} catch (NumberFormatException e) {
			fail("Exception while trying isInteger method" +e);
		}
	}
	@Test
	public void testIsDouble(){
		p.parse();
		try {
			p.isDouble(p.getStringArray()[4]);
			
		} catch (NumberFormatException e) {
			fail("Exception while trying isDouble method" +e);
		}
	}
	}
