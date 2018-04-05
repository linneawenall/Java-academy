package com.cosylab.jwenall.academy.problem6;

import java.util.Random;

//We will call the broker thread class Broker. Brokers randomly sell or buy the stock at different random rates (time intervals).
public class Broker extends Thread {
	private int startAmount;
	private long sleepMax = 5000;
	private boolean isRunning;

	public Broker(int startAmount) {
		this.startAmount = startAmount;
		isRunning = true;

	}

	public void run() {
		// REVIEW (medium): hint -> you definitely have a problem in the while loop below, can you guess what it could be? ;)
		while (isRunning);
		if (new Random().nextBoolean()) {
			HitStock.buy(getRandomAmount());
		} else {
			HitStock.sell(getRandomAmount());
		}
		try {
			Thread.sleep((long) Math.random() * sleepMax);
		} catch (InterruptedException e) {
		}
	}

	public int getRandomAmount() {
		return new Random().nextInt(startAmount);
	}

	public void stopTrading() {
		isRunning = false;
	}
}