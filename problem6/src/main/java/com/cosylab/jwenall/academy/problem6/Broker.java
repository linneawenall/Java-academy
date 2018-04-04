package com.cosylab.jwenall.academy.problem6;

//We will call the broker thread class Broker. Brokers randomly sell or buy the stock at different random rates (time intervals).
public class Broker extends Thread {
	private HitStock hitStock;
	private long sleepMax = 5000;

	// REVIEW (high): when HitStock class will be implemented as a singleton object you won't need to pass it as an argument
	// to the "Broker" constructor. You even won't need the "private HitStock hitStock;" variable definition.
	// You will be able to use it directly from the "run" method.
	public Broker(HitStock hitStock) {
		this.hitStock = hitStock;

	}

	public void run() {
		int counter = 0; // The counter and the break is just to make it not run
							// forever

		// REVIEW (medium): it would make sense introducing the "isRunning" boolean variable and "stopTrading" public
		// method. This method would control when the broker should stop (you could call this method from the "main" method).
		while (isAlive()) {
			counter++;
			if (counter == 10) {
				interrupt();
				break;
			}
			// REVIEW (medium): it would be better if you use the random boolean to determine whether the broker is buying or selling.
			if (Math.random() >= 0.5) {
				try {
					hitStock.buy(getRandomAmount());
					// REVIEW (medium): after the code change, you probably won't need the "try-catch" anymore.
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
		// REVIEW (low): was the formula for the random amount defined in the task instructions or did you make it up yourself?
		// If it wasn't defined in the instructions, you could perhaps use something simpler, for example:
		// Random.nextInt(startAmount). Also you should get start amount as an argument from "Broker" constructor
		// rather than from the "hitStock.getStartAmount()".
		return (int) (Math.random() * hitStock.getStartAmount() / 3 + 1);
	}
}
