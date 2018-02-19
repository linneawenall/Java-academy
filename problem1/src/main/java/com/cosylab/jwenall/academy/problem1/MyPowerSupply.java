package com.cosylab.jwenall.academy.problem1;

public class MyPowerSupply {
	private double current;
	private boolean power;

	public MyPowerSupply() {
		this.current = 0;
		this.power = false;
	}

	// REVIEW (medium): "throws" statement missing.
	public void on() {
		if (!isPowerOn()) {
			power = true;
			set(0.0);
		} else
			throw new IllegalStateException("Power is already switched ON");
	}

	// REVIEW (medium): "throws" statement missing.
	public void off() {
		if (isPowerOn()) {
			power = false;
		} else
			throw new IllegalStateException("Power is already switched OFF");
	}

	// REVIEW (medium): "throws" statement missing.
	public void reset() {
		if (isPowerOn()) {
			current = 0.0;
		} else
			throw new IllegalStateException("Power is turned OFF, resetting failed.");

	}

	// REVIEW (medium): "throws" statement missing.
	public double get() {
		if (isPowerOn()) {
			return current;
		} else
			throw new IllegalStateException("Power is turned OFF");
	}

	// REVIEW (medium): "throws" statement missing.
	public void set(double value) {
		// REVIEW (medium): can you think of some additional error cases for this method?
		// Hint: what do you think would happen if you tried to set the current to a negative vlaue?
		if (isPowerOn()) {
			current = value;
		} else
			throw new IllegalStateException("Couldn't set value because power is turned OFF");

	}

	// REVIEW (minor): you probably don't need this method. Simply checking of the "power" variable is enough.
	public boolean isPowerOn(){
		return power;
	}

}
