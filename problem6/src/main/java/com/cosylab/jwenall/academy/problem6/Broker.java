package com.cosylab.jwenall.academy.problem6;

import java.util.Random;

//We will call the broker thread class Broker. Brokers randomly sell or buy the stock at different random rates (time intervals).
public class Broker extends Thread {
	private int startAmount;
	private long sleepMax = 5000;
	private volatile Thread brokerThread;

	public Broker(int startAmount) {
		this.startAmount = startAmount;
		brokerThread = this;

	}

	public void run() {
		Thread thisThread = Thread.currentThread();
		while (thisThread == brokerThread) {
			if (new Random().nextBoolean()) {
				HitStock.buy(getRandomAmount());
			} else {
				HitStock.sell(getRandomAmount());
			}
			try {
				Thread.sleep((long) Math.random() * sleepMax);
			} catch (InterruptedException e) {
				break;
			}
		}
	}

	public int getRandomAmount() {
		return new Random().nextInt(startAmount);
	}

	public void stopTrading() {
		brokerThread = null;

	}
}