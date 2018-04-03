package com.cosylab.jwenall.academy.problem6;

public class Stock { // maybe add final - should be singleton

	private static Stock instance = null;

	protected Stock() {
		// Exists only to defeat instantiation.
	}

	public static Stock getInstance() {
		if (instance == null) {
			instance = new Stock();
		}
		return instance;
	}
}
