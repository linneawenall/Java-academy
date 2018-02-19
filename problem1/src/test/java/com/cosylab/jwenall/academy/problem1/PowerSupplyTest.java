package com.cosylab.jwenall.academy.problem1;

import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;

/**
 * PowerSupplyTest contains tests for PowerSupply interface.
 */
public class PowerSupplyTest {
	MyPowerSupply mps;
	RampedPowerSupply rps;
	double[] rampValues;

	@Before
	public void setUp() throws Exception {
		mps = new MyPowerSupply();
		rps = new RampedPowerSupply();
		rampValues = new double[10];
		for (int i = 0; i < 10; i++) {
			rampValues[i] = i;
		}
	}

	@After
	public void tearDown() throws Exception {

	}

	/**
	 * Test if the power supply can be turned on.
	 */
	@Test
	public void testOn() {
		try {
			mps.on();
		} catch (IllegalStateException exception) {
			fail("Exception while turning the power supply on: " + exception);
		}
	}

	@Test
	public void testOff() {
		try {
			mps.on();
			mps.off();
		} catch (IllegalStateException exception) {
			fail("Exception while turning the power supply off: " + exception);
		}
	}

	/* 1. presenting the full functionality of the RampedPowerSupply, */
	@Test
	public void testOnRamped() {
		try {
			rps.on();
		} catch (IllegalStateException exception) {
			fail("Exception while turning the power supply on: " + exception);
		}
	}

	@Test
	public void testOffRamped() {
		try {
			rps.on();
			rps.off();
		} catch (IllegalStateException exception) {
			fail("Exception while turning the power supply off: " + exception);
		}
	}

	@Test
	public void testResetRamped() {
		try {
			rps.on();
			rps.reset();
		} catch (IllegalStateException exception) {
			fail("Exception while resetting rps: " + exception);
		}
	}

	@Test
	public void testGetRamped() {
		try {
			rps.on();
			rps.set(50);
			rps.get();
		} catch (IllegalStateException exception) {
			fail("Exception while calling get method when power is on: " + exception);
		}
		assertTrue("Current is 50", rps.get() == 50);
	}

	@Test
	public void testSetRamped() {
		try {
			rps.on();
			rps.set(50);
		} catch (IllegalStateException exception) {
			fail("Exception while calling set method when power is on: " + exception);
			assertTrue("Current is 50", rps.get() == 50);
		}
	}

	@Test
	public void testLoadRamped() {
		try {
			rps.on();
			rps.loadRamp(rampValues);
			for (int i = 0; i < rps.getRampValues().length; i++) {
				System.out.println("RampValues:" + rps.getRampValues()[i]);
			}
		} catch (NullPointerException exception) {
			fail("Exception while calling loadramp method when rampValues is null: " + exception);
		}
	}

	@Test
	public void testStartRamp() {
		try {
			rps.on();
			rps.loadRamp(rampValues);
			rps.startRamp(100);
		} catch (IllegalStateException exception) {
			fail("Exception while calling startramp method when power is off or it's already ramping: " + exception);
		}
	}

	@Test
	public void testRun() throws InterruptedException {
		rps.on();
		rps.loadRamp(rampValues);
		rps.startRamp(1000);
		rps.run();
		for (int i = 0; i < rps.getPostRamping().length; i++) {
			System.out.println("PostRampCurrent: " + rps.getPostRamping()[i]);
		}
	}

	/*
	 * 2. proving that illegal operations from Requirements (2.1.) are prevented
	 * and,
	 */

	/*
	 * 3. one that includes a ramping RampedPowerSupply, with the test querying
	 * the current in a loop as described in Sample Output (3.2.).
	 */
}
