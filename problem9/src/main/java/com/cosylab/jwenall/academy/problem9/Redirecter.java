package com.cosylab.jwenall.academy.problem9;

public interface Redirecter {

	//Notifies that device changed its state.
	public void onState(boolean state);

	//Notifies that device changed its ramping state.
	public void rampingState(boolean state);

	//Notifies that the current value is changed.
	public void updateValue(double value);

	//Updates log screen.
	public void logUpdate(String text);

}
