package com.cosylab.jwenall.academy.problem1;

public class MyPowerSupply {
	private double current;
	protected boolean power;

	public MyPowerSupply() {
		this.current = 0;
		this.power = false;
	}

	// REVIEW (medium): "throws" statement missing.
	public void on() throws IllegalStateException {
		if (!power) {
			power = true;
			set(0.0);
		} else
			throw new IllegalStateException("Power is already switched ON");
	}

	// REVIEW (medium): "throws" statement missing.
	public void off()throws IllegalStateException {
		if (power) {
			power = false;
		} else
			throw new IllegalStateException("Power is already switched OFF");
	}

	// REVIEW (medium): "throws" statement missing.
	public void reset() throws IllegalStateException {
		if (power) {
			current = 0.0;
		} else
			throw new IllegalStateException("Power is turned OFF, resetting failed.");

	}

	// REVIEW (medium): "throws" statement missing.
	public double get()throws IllegalStateException {
		if (power) {
			return current;
		} else
			throw new IllegalStateException("Power is turned OFF");
	}

	// REVIEW (medium): "throws" statement missing.
	public void set(double value) throws IllegalStateException{
		// REVIEW (medium): can you think of some additional error cases for this method?
		// Hint: what do you think would happen if you tried to set the current to a negative vlaue?
		if(value<0){
			throw new IllegalArgumentException("Current value has to be bigger than 0");
		}
		if (power) {
			current = value;
		} else
			throw new IllegalStateException("Couldn't set value because power is turned OFF");
		
	}
}
