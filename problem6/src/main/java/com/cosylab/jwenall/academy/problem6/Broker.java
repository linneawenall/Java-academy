package com.cosylab.jwenall.academy.problem6;

//We will call the broker thread class Broker. Brokers randomly sell or buy the stock at different random rates (time intervals).
public class Broker extends Thread {
	private Stock stock;

	// By declaring the counter variable volatile all writes to the counter
	// variable will be written back to main memory immediately. Also, all reads
	// of the counter variable will be read directly from main memory
	private volatile Thread brokerThread;

	public Broker() {
		brokerThread = this;
	}
}
