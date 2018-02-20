package com.cosylab.jwenall.academy.problem1;

public interface RampedPowerSupply extends PowerSupply{


	public void startRamp(int msecs);
	
	public void loadRamp(double[] rampValues);

}
