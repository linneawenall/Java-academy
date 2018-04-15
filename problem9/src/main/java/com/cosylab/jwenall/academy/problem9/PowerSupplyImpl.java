package com.cosylab.jwenall.academy.problem9;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// Did you perhaps forget to commit it?
public class PowerSupplyImpl implements PowerSupply {
	private double current;
	protected boolean power;
	private Redirecter redirecterInterface;
	private ReadWriteLock lock = new ReentrantReadWriteLock();

	public PowerSupplyImpl() {
		this.current = 0.0;
		this.power = false;
		System.out.println("creating powersupply with power = false");
	}

	public void on() throws IllegalStateException {
		if (!power) {
			power = true;
			if (redirecterInterface != null) {
				redirecterInterface.onState(true);
				redirecterInterface.logUpdate("Power supply turned ON");
			}
			System.out.println("Power is turned on");
			set(0.0);
		} else
			throw new IllegalStateException("Power is already switched ON");
	}

	public void off() throws IllegalStateException {
		if (power) {
			power = false;
			if (redirecterInterface != null) {
				redirecterInterface.onState(false);
				redirecterInterface.logUpdate("Power supply turned OFF");
			}
			System.out.println("Power is turned off");
		} else
			throw new IllegalStateException("Power is already switched OFF");
	}

	public void reset() throws IllegalStateException {
		if (power) {
			current = 0.0;
			if (redirecterInterface != null) {
				redirecterInterface.logUpdate("Power supply reseted");
			}
		} else
			throw new IllegalStateException("Power is turned OFF, resetting failed.");

	}

	public double get() throws IllegalStateException {
		if (power) {
			lock.readLock().lock();
			try {
				return current;
			} finally {
				lock.readLock().unlock();
			}
		} else
			throw new IllegalStateException("Exception: Power is turned OFF");
	}

	public void set(double value) throws IllegalStateException, IllegalArgumentException {
		System.out.println("Trying to set value to: " + value);
		if (value < 0) {
			throw new IllegalArgumentException("Current value has to be bigger than 0");
		}
		if (power) {
			System.out.println("Setting current to: " + value);
			lock.writeLock().lock();
			try {
				current = value;
				if (redirecterInterface != null) {
					redirecterInterface.updateValue(current);
					redirecterInterface.logUpdate("Setting current to: " + value);
				}
			} finally {
				lock.writeLock().unlock();
			}

		} else
			throw new IllegalStateException("Couldn't set value because power is turned OFF");

	}

	public void addRedirecter(Redirecter redirecterInterface) {
		this.redirecterInterface = redirecterInterface;
	}

	public void removeRedirecter() {
		this.redirecterInterface = null;
	}

	public Redirecter getRedirecter() {
		return redirecterInterface;
	}
}
