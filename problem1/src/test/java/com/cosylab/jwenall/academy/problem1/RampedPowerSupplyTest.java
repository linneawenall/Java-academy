package com.cosylab.jwenall.academy.problem1;

import org.junit.Test;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;

import java.util.concurrent.Callable;

// REVIEW (high): move the RampedPowerSupply tests to a separate "RampedPowerSupplyTest" file.
/**
 * PowerSupplyTest contains tests for PowerSupply interface.
 */
public class RampedPowerSupplyTest {
	//MyPowerSupply mps;
	RampedPowerSupplyImpl rps;
	double[] rampValues;

	@Before
	public void setUp() throws Exception {
		//mps = new MyPowerSupply();
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
//	@Test
//	public void testOn() {
//		try {
//			mps.on();
//		} catch (IllegalStateException exception) {
//			fail("Exception while turning the power supply on: " + exception);
//		}
//	}
//
//	@Test
//	public void testOff() {
//		try {
//			mps.on();
//			mps.off();
//		} catch (IllegalStateException exception) {
//			fail("Exception while turning the power supply off: " + exception);
//		}
//	}

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

	// REVIEW (medium): you can remove this test. The "loadRamp" method is tested as a part of the "testStartRamp" test.
	@Test
	public void testLoadRamped() {
		try {
			rps.on();
			rps.loadRamp(rampValues);
//			for (int i = 0; i < rps.getRrampValues().length; i++) {
//				System.out.println("RampValues:" + rps.getRampValues()[i]);
//			}
		} catch (NullPointerException exception) {
			fail("Exception while calling loadramp method when rampValues is null: " + exception);
		}
	}

	// REVIEW (high): this test of the "startRamp" is not correct, because it doesn't wait for the ramping to finish.
	// The ramping runs as a separate thread, so the "startRamp" method returns immediately. As a consequence this
	// test finishes muchearlier than the ramping itself.
	// You can find the proper implementation below.
	/*@Test
	public void testStartRamp() {
		try {
			rps.on();
			rps.loadRamp(rampValues);
			rps.startRamp(100);
		} catch (IllegalStateException exception) {
			fail("Exception while calling startramp method when power is off or it's already ramping: " + exception);
		}
	}*/

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

	// REVIEW (high): the "testRun" test is not valid. The "run" method should never be called explicitly. It is always
	// called automatically by the thread it is provided to.
	// If you look at the "testStartRamp" ramp test, the newly-created thread inside the "RampedPowerSupply" class
	// will start executing the "run" function automatically after the "rps.startRamp(msecs);" is executed.
	/*@Test
	public void testRun() throws InterruptedException {
		rps.on();
		rps.loadRamp(rampValues);
		rps.startRamp(1000);
		rps.run();
		for (int i = 0; i < rps.getPostRamping().length; i++) {
			System.out.println("PostRampCurrent: " + rps.getPostRamping()[i]);
		}
	}*/

	/*
	 * 2. proving that illegal operations from Requirements (2.1.) are prevented
	 * and,
	 */

	/*
	 * 3. one that includes a ramping RampedPowerSupply, with the test querying
	 * the current in a loop as described in Sample Output (3.2.).
	 */
}
