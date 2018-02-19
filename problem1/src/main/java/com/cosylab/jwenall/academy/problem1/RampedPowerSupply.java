package com.cosylab.jwenall.academy.problem1;

import java.util.Arrays;

public class RampedPowerSupply extends MyPowerSupply implements Runnable {
	private boolean ramping;
	private double[] rampValues;
	private int msecs;
	private Thread threadRamper;
	private double[] postRamping;


	public RampedPowerSupply() {
		super();
		ramping = false;
	}

	// REVIEW (medium): "throws" statement missing.
	/* Will turn the power off. */
	public void off() {
		if (isRamping()) {
			try {
				threadRamper.interrupt();
			} catch (Exception e) { // Which exception?
				ramping = false;
			}
		}
		super.off();
	}

	// REVIEW (medium): "throws" statement missing.
	/* Will reset the power supply to 0. */
	public void reset() {
		if (isRamping()) {
			try {
				threadRamper.interrupt();
			} catch (Exception e) { // Which exception?
				ramping = false;
			}
		}
		super.reset();
	}

	// REVIEW (medium): "throws" statement missing.
	/* Will set the current in the power supply to the given value. */
	public void set(double value) {
		if (!isRamping()) {
			super.set(value);
		} else
			throw new IllegalStateException("Can not set value, power is ramping");

	}

	// REVIEW (medium): "throws" statement missing.
	/* Loads ramp values given as an array of double values. */
	public void loadRamp(double[] rampValues) {
		if (rampValues == null || rampValues.length < 1) {
			throw new NullPointerException("The array of values is empty");
		}
		if (isPowerOn()) {
			Arrays.sort(rampValues); //Do I want the values sorted?
			this.rampValues = new double[rampValues.length];
			System.arraycopy(rampValues, 0, this.rampValues, 0, rampValues.length);
		} else if (!isPowerOn()) {
			throw new IllegalStateException("Can not load ramp values, power is OFF");
		}
		if (isRamping()) {
			throw new IllegalStateException("Can not load ramp values, power is already ramping");
		}
	}

	/* Ramps in time intervals with duration of msecs. */
	public void startRamp(int msecs) {
		if (isRamping()) {
			throw new IllegalStateException("Power supply is already ramping");
		}
		if (!isPowerOn()) {
			throw new IllegalStateException("Power is turned OFF");
		} else {
			this.msecs = msecs;
			this.postRamping = new double[rampValues.length];
			new Thread(threadRamper).start();
		}

	}

	@Override
	public void run() { 
		for (int i = 0; i <= rampValues.length-1; i++) {
			try {
				super.set(rampValues[i]);
				postRamping[i] = get(); //3.1 By putting such a call in a loop all the ramp values should be passed as the rps ramps.
				Thread.sleep(msecs);

			} catch (InterruptedException e) {
			}
		}
	}

	public boolean isRamping() {
		return ramping;
	}

	public double[] getRampValues() {
		return rampValues;
	}

	public double[] getPostRamping() {
		return postRamping;
	}
}
