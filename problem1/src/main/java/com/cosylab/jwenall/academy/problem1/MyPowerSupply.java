package com.cosylab.jwenall.academy.problem1;

public class MyPowerSupply {
	private double current;
	private boolean power;

	public MyPowerSupply() {
		this.current = 0;
		this.power = false;
	}


	public void on() {
		if (!isPowerOn()) {
			power = true;
			set(0.0);
		} else
			throw new IllegalStateException("Power is already switched ON");
	}


	public void off() {
		if (isPowerOn()) {
			power = false;
		} else
			throw new IllegalStateException("Power is already switched OFF");
	}


	public void reset() {
		if (isPowerOn()) {
			current = 0.0;
		} else
			throw new IllegalStateException("Power is turned OFF, resetting failed.");

	}


	public double get() {
		if (isPowerOn()) {
			return current;
		} else
			throw new IllegalStateException("Power is turned OFF");
	}

	public void set(double value) {
		if (isPowerOn()) {
			current = value;
		} else
			throw new IllegalStateException("Couldn't set value because power is turned OFF");

	}
	public boolean isPowerOn(){
		return power;
	}

}
