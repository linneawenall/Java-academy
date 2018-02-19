package com.cosylab.jwenall.academy.problem1;

public class Main {
	public static void main(String[] args) {
		RampedPowerSupply rps = new RampedPowerSupply();
		double[] rampValues = { 10.2, 12.3, 13.5, 15.1, 18.4, 18.5, 20.5 };
		int msecs = 1000;
		rps.on();
		rps.loadRamp(rampValues);
		rps.startRamp(msecs);
		int numRampValues = rampValues.length;
	}
}
