package com.cosylab.jwenall.academy.problem3;

public interface RampedPowerSupply extends PowerSupply {

	/* Ramps in time intervals with duration of msecs. */
	public void startRamp(int msecs);

	/* Loads ramp values given as an array of double values. */
	public void loadRamp(double[] rampValues);

}
