package com.cosylab.jwenall.academy.problem6;

//We will call the broker thread class Broker. Brokers randomly sell or buy the stock at different random rates (time intervals).
public class Broker extends Thread {
	private HitStock hitStock;
	private long sleepMax = 5000;

	public Broker(HitStock hitStock) {
		this.hitStock = hitStock;

	}

	public void run() {
		int counter = 0; // The counter and the break is just to make it not run
							// forever
		while (isAlive()) {
			counter++;
			if (counter == 10) {
				interrupt();
				break;
			}
			if (Math.random() >= 0.5) {
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
	}

	public int getRandomAmount() {
		return (int) (Math.random() * hitStock.getStartAmount() / 3 + 1);
	}
}
