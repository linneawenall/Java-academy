package com.cosylab.jwenall.academy.problem9;

import java.util.Arrays;


public class RampedPowerSupplyImpl extends PowerSupplyImpl implements RampedPowerSupply {
	private boolean ramping;
	protected double[] rampValues;
	private int msecs;
	protected double[] postRamping;
	Thread ramperThread;

	public RampedPowerSupplyImpl() {
		super();
		ramping = false;
	}

	/* Will turn the power off. */
	public void off() throws IllegalStateException {
		if (isRamping()) {
			try {
				ramperThread.interrupt();
			} catch (SecurityException se) {
				System.out.println(se.getMessage());
			}
			ramping = false;
		}
		if (getRedirecter() != null)
			getRedirecter().rampingState(false);
		super.off();
	}

	/* Will reset the power supply to 0. */
	public void reset() throws IllegalStateException {
		if (isRamping()) {
			try {
				ramperThread.interrupt();
			} catch (SecurityException se) {
				System.out.println(se.getMessage());
			}
			ramping = false;
			if (getRedirecter() != null)
				getRedirecter().rampingState(false);
		}
		super.reset();
	}

	/* Will set the current in the power supply to the given value. */
	public void set(double value) throws IllegalStateException {
		if (!isRamping()) {
			super.set(value);
		} else
			throw new IllegalStateException("Can not set value, power is ramping");

	}

	/* Loads ramp values given as an array of double values. */
	public void loadRamp(double[] rampValues) throws IllegalStateException {
		System.out.println("Loading ramp values");
		if (rampValues == null || rampValues.length < 1) {
			throw new NullPointerException("The array of values is empty");
		}
		if (power) {
			Arrays.sort(rampValues);
			this.rampValues = new double[rampValues.length];
			System.arraycopy(rampValues, 0, this.rampValues, 0, rampValues.length);
			for (int i = 0; i <= rampValues.length - 1; i++) {
				System.out.println("RampValue at index: " + i + ": " + rampValues[i]);
			}
		} else if (!power) {
			throw new IllegalStateException("Can not load ramp values, power is OFF");
		}
		if (isRamping()) {
			throw new IllegalStateException("Can not load ramp values, power is already ramping");
		}
	}

	/* Ramps in time intervals with duration of msecs. */
	public void startRamp(int msecs) throws IllegalStateException {
		if (isRamping()) {
			throw new IllegalStateException("Power supply is already ramping");
		}
		if (!power) {
			throw new IllegalStateException("Power is turned OFF");
		} else {
			// ramping=true was added.
			ramping = true;
			this.msecs = msecs;
			this.postRamping = new double[rampValues.length];
			ramperThread = new Thread(new Ramper());
			ramperThread.start();
		}
		System.out.println("Startramp");

	}

	public boolean isRamping() {
		return ramping;
	}

	private class Ramper implements Runnable {

		@Override
		public void run() { // These 3 lines were added
			if (getRedirecter() != null) {
				getRedirecter().rampingState(true);
				getRedirecter().logUpdate("Ramping started.");
			}
			System.out.println("Ramper is running");
			for (int i = 0; i <= rampValues.length - 1; i++) {
				try {
					System.out.println("Loop number: " + i);
					set(rampValues[i]);
					Thread.sleep(msecs);

				} catch (InterruptedException e) {
				}
			}
			ramping = false;
			if (getRedirecter() != null) {
				getRedirecter().rampingState(false);
				getRedirecter().logUpdate("Ramping ended.");
			}

		}
	}
	
	public boolean isInterrupted() {
		return ramperThread.isInterrupted();
	}

	public boolean isOn() {
		return power;
	}
}
