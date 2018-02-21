package com.cosylab.jwenall.academy.problem1;

// REVIEW (high): currently the code doesn't compile for me because "PowerSupply" interface is missing.
// Did you perhaps forget to commit it?
public class PowerSupplyImpl implements PowerSupply {
	private double current;
	protected boolean power;

	public PowerSupplyImpl() {
		this.current = 0;
		this.power = false;
	}

	public void on() throws IllegalStateException {
		if (!power) {
			power = true;
			set(0.0);
		} else
			throw new IllegalStateException("Power is already switched ON");
	}

	public void off()throws IllegalStateException {
		if (power) {
			power = false;
		} else
			throw new IllegalStateException("Power is already switched OFF");
	}

	public void reset() throws IllegalStateException {
		if (power) {
			current = 0.0;
		} else
			throw new IllegalStateException("Power is turned OFF, resetting failed.");

	}

	public double get()throws IllegalStateException {
		if (power) {
			return current;
		} else
			throw new IllegalStateException("Power is turned OFF");
	}


	public void set(double value) throws IllegalStateException, IllegalArgumentException{
		if(value<0){
			throw new IllegalArgumentException("Current value has to be bigger than 0");
		}
		if (power) {
			current = value;
		} else
			throw new IllegalStateException("Couldn't set value because power is turned OFF");
		
	}
}
