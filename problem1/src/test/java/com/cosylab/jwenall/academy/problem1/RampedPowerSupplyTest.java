package com.cosylab.jwenall.academy.problem1;

import org.junit.Test;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;

import java.util.concurrent.Callable;


/**
 * RampedPowerSupplyTest contains tests for RampedPowerSupply interface.
 */
public class RampedPowerSupplyTest {

	RampedPowerSupplyImpl rps;
	double[] rampValues;

	@Before
	public void setUp() throws Exception {
		rps = new RampedPowerSupplyImpl();
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


	// REVIEW (high): your implementation of the "RampedPowerSupply" will need to pass this test.
	@Test
	public void testStartRamp() throws IllegalAccessException, InterruptedException {
		// loading an array of values. double[]
		double[] rampValues = { 10.2, 12.3, 13.5, 15.1, 18.4, 18.5, 20.5 };

		// turn power supply on
		rps.on();
		// load ramping values
		rps.loadRamp(rampValues);
		// define ramping interval in msecs and start ramping
		int msecs = 1000;
		rps.startRamp(msecs);
		int numRampValues = rampValues.length;
		await().atMost(msecs * numRampValues, MILLISECONDS).until(hasRampingFinished());
		// Thread.sleep(msecs * numRampValues);
		assertEquals(rampValues[numRampValues - 1], rps.get(), 0.0);
	}

	private Callable<Boolean> hasRampingFinished() {
		return new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return rps.get() == 20.5;
			}
		};
	}
}