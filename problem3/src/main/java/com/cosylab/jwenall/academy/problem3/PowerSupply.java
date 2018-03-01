package com.cosylab.jwenall.academy.problem3;

public interface PowerSupply {


	/* Will turn the power on. */
	public void on();

	/* Will turn the power off. */
	public void off();

	/* Will reset the power supply to 0. */
	public void reset();

	/* Will return the current that power supply gives out. */
	public double get();

	/* Will set the current in the power supply to the given value. */
	public void set(double value);

}
