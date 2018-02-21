package com.cosylab.jwenall.academy.problem2;

import org.junit.Test;

import com.cosylab.jwenall.academy.problem1.RampedPowerSupplyImpl;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;

public class SortingAlgorithmTest {
	SortingAlgorithm sort;

	@Before
	public void setUp() throws Exception {
		sort = new SortingAlgorithm ();
		}
	

	@After
	public void tearDown() throws Exception {

	}
	
	@Test
	public void testSortList() {
		try {
			sort.sortList();
		} catch (IllegalStateException exception) {
			fail("Exception while turning the power supply on: " + exception);
		}
	}

}
