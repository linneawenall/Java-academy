package com.cosylab.jwenall.academy.problem1;

import java.util.Arrays;

// REVIEW (high): I see you have "on", "off", "reset", "set", "get" methods defined in both classes,
// "MyPowerSupply" and "RampedPowerSupply". Do you think it would be possible for those methods to be defined
// only in "MyPowerSupply" ("RampedPowerSupply" would, of course, inherit them from "MyPowerSupply")?
public class RampedPowerSupply extends MyPowerSupply {
	private boolean ramping;
	protected double[] rampValues;
	private int msecs;
	//private Thread threadRamper;
	protected double[] postRamping;
	private Ramper ramper;
	Thread ramperThread = new Thread(ramper);
	private GetThread getter;
	Thread getThread = new Thread (getter);
	

	public RampedPowerSupply() { 
		super();
		ramping = false;
	}

	// REVIEW (medium): "throws" statement missing.
	/* Will turn the power off. */
	public void off() throws IllegalStateException {
		if (isRamping()) {
			try {
				ramperThread.interrupt();
			} catch (IllegalStateException e) { // Which exception?
				ramping = false;
			}
		}
		super.off();
	}

	// REVIEW (medium): "throws" statement missing.
	/* Will reset the power supply to 0. */
	public void reset() throws IllegalStateException {
		if (isRamping()) {
			try {
				ramperThread.interrupt();
			} catch (IllegalStateException e) { // Which exception?
				ramping = false;
			}
		}
		super.reset();
	}

	// REVIEW (medium): "throws" statement missing.
	/* Will set the current in the power supply to the given value. */
	public void set(double value)throws IllegalStateException {
		if (!isRamping()) {
			super.set(value);
		} else
			throw new IllegalStateException("Can not set value, power is ramping");

	}

	// REVIEW (medium): "throws" statement missing.
	/* Loads ramp values given as an array of double values. */
	public void loadRamp(double[] rampValues) throws IllegalStateException {
		if (rampValues == null || rampValues.length < 1) {
			throw new NullPointerException("The array of values is empty");
		}
		if (power) {
			Arrays.sort(rampValues); // Do I want the values sorted?
			this.rampValues = new double[rampValues.length];
			System.arraycopy(rampValues, 0, this.rampValues, 0, rampValues.length);
		} else if (!power) {
			throw new IllegalStateException("Can not load ramp values, power is OFF");
		}
		if (isRamping()) {
			throw new IllegalStateException("Can not load ramp values, power is already ramping");
		}
	}

	/* Ramps in time intervals with duration of msecs. */
	public void startRamp(int msecs)throws IllegalStateException {
		if (isRamping()) {
			throw new IllegalStateException("Power supply is already ramping");
		}
		if (!power) {
			throw new IllegalStateException("Power is turned OFF");
		} else {
			this.msecs = msecs;
			this.postRamping = new double[rampValues.length];
			// you probably for got to assign
			// REVIEW (high): it looks to me like you have an issue here. You
			// don't assign the thread you are creating
			// below to any variable. This means the newly-created thread will
			// probably get desrtoyed once the
			// "startRamp" method finishes.
			// Also, in your case, the newly-created thread needs to get a
			// reference to a class that implements
			// the stuff the thread will do, in a form of a "public void run()"
			// method.
			ramperThread.start();
			getThread.start();
		}

	}

	// REVIEW (high): the implementation of the "run" method is correct.
	// However, the "run" method shouldn't be
	// implemented as a part of "RampedPowerSupply", it needs to go somehwere
	// else... ;)
	// Hint: read
	// http://tutorials.jenkov.com/java-concurrency/creating-and-starting-threads.html
//	@Override
//	public void run() {
//		for (int i = 0; i <= rampValues.length - 1; i++) {
//			try {
//				super.set(rampValues[i]);
//				postRamping[i] = get(); // 3.1 By putting such a call in a loop
//										// all the ramp values should be passed
//										// as the rps ramps.
//				Thread.sleep(msecs);
//
//			} catch (InterruptedException e) {
//			}
//		}
//	}

	public boolean isRamping() {
		return ramping;
	}

	// REVIEW (medium): you probably don't need this method.
	public double[] getPostRamping() {
		return postRamping;
	}

	private class Ramper implements Runnable {

		@Override
		public void run() {
			for (int i = 0; i <= rampValues.length - 1; i++) {
				try {
					set(rampValues[i]);
					//postRamping[i] = get();
					Thread.sleep(msecs);

				} catch (InterruptedException e) {
				}
			}

		}
	}

		private class GetThread implements Runnable {

			@Override
			public void run() {
				for (int i = 0; i <= rampValues.length - 1; i++) {
					try {
						postRamping[i] = get();
						System.out.println(postRamping[i]);
						Thread.sleep(msecs);

					} catch (InterruptedException e) {
					}
				}

			}
		}
	}
