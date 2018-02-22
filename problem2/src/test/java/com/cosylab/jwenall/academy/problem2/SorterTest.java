package com.cosylab.jwenall.academy.problem2;

import org.junit.Test;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;

public class SorterTest {
	Sorter s;
	int order;
	String[] input;

	@Before
	public void setUp() throws Exception {
		s = new Sorter(order, input);
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testSortList() {
		try {
			s.sortList();
		} catch (IllegalArgumentException exception) {
			fail("Exception when sorting with sortList method: " + exception);
		}
	}

	@Test
	public void testaddNumber() {
		try {
			s.addNumber(input);
		} catch (IllegalArgumentException exception) {
			fail("Exception: Error when calling addNumber method" + exception);
		}
	}

	@Test
	public void testIsInteger() {
		try {
			s.isInteger(input[1]);
		} catch (NumberFormatException e) {
			try {
				s.isDouble(input[1]);
			} catch (NumberFormatException ex) {
			}
		}
	}
}
