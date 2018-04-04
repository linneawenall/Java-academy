package com.cosylab.jwenall.academy.problem6;

// REVIEW (high): actually, you don't need this class. The singleton pattern should be implemented as a part of "HitStock" class.
public class Stock { // maybe add final - should be singleton

	private static Stock instance = null;

	protected Stock() {
		// Exists only to defeat instantiation.
	}

	public static Stock getInstance() {
		if (instance == null) {// not thread-safe
			instance = new Stock();
		}
		return instance;
	}
}
