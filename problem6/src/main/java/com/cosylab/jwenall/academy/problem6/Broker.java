package com.cosylab.jwenall.academy.problem6;

//We will call the broker thread class Broker. Brokers randomly sell or buy the stock at different random rates (time intervals).
public class Broker extends Thread {
	private HitStock hitStock;
	private long sleepMax = 50000;

	public Broker(HitStock hitStock) {
		this.hitStock = hitStock;
	}

	@Override
	public void run() {
		// System.out.println("Entering run in Broker for " +
		// Thread.currentThread().getName());
		if (Math.random() > 0.5) {
			try {
				hitStock.buy(getRandomAmount());
			} catch (IllegalStateException ignoreException) {
			}
		} else {
			hitStock.sell(getRandomAmount());
		}
		try {
			Thread.sleep((long) Math.random() * sleepMax);
		} catch (InterruptedException e) {
		}
	}

	public int getRandomAmount() {
		// System.out.println("In getRandomAmount in Broker for " +
		// Thread.currentThread().getName());
		return (int) (Math.random() * hitStock.getStartAmount());
	}
}
