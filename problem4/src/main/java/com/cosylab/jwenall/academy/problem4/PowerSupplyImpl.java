package com.cosylab.jwenall.academy.problem4;


public class PowerSupplyImpl implements PowerSupply {
	private double current;
	protected boolean power;

	public PowerSupplyImpl() {
		this.current = 0;
		this.power = false;
		System.out.println("creating powersupply with power = false");
	}

	public void on() throws IllegalStateException {
		if (!power) {
			power = true;
			System.out.println("Power is turned on");
			set(0.0);
		} else
			throw new IllegalStateException("Power is already switched ON");
	}

	public void off()throws IllegalStateException {
		if (power) {
			power = false;
			System.out.println("Power is turned off");
		} else
			throw new IllegalStateException("Power is already switched OFF");
	}

	public void reset() throws IllegalStateException {
		if (power) {
			System.out.println("Resetting current to 0.0");
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
		System.out.println("Trying to set value to: " +value);
		if(value<0){
			throw new IllegalArgumentException("Current value has to be bigger than 0");
		}
		if (power) {
			System.out.println("Setting current to: " + value);
			current = value;
		} else
			throw new IllegalStateException("Couldn't set value because power is turned OFF");
		
	}
}
