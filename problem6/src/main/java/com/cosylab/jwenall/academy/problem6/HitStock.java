package com.cosylab.jwenall.academy.problem6;

//This class holds records of how many stocks are available on the market only for our "Hit" stock. For buying and selling we will have corresponding class methods. One stock will suffice for our objectives.

//The HitStock class gathers requests from various brokers, which all run in their separate threads. 
public class HitStock {
	// By declaring the counter variable volatile all writes to the counter
	// variable will be written back to main memory immediately. Also, all reads
	// of the counter variable will be read directly from main memory
	// private volatile Thread brokerThread;

	private volatile int availableStocks;
	private static final int startAmount = 800; // stock opening balance

	public  HitStock() {
		// synchronized(X.class) is used to make sure that there is exactly one
		// Thread in the block.
		synchronized (HitStock.class) {
			Stock stock = Stock.getInstance();
			availableStocks = 800;
				System.out.println("Stock opening balance: " + startAmount);
		}
	}

	public synchronized void buy(int stockAmount) {
		//System.out.println("buy request for " + stockAmount + " for " + Thread.currentThread());
		if (availableStocks >= stockAmount) {
			System.out.println("BUY of " + stockAmount + " by " + Thread.currentThread());
			synchronized(this){
			availableStocks = availableStocks - stockAmount;
			System.out.println("New Balance " + availableStocks);
			}
		} else {
			System.out.println("NOT ENOUGH STOCKS.");
			throw new IllegalStateException();

		}
	}

	public synchronized void sell(int stockAmount) {
		//System.out.println("sell request for " + stockAmount + " for " + Thread.currentThread());
		System.out.println("SELL of " + stockAmount + " by " + Thread.currentThread());
		synchronized(this){
		availableStocks = availableStocks + stockAmount;
		System.out.println("New Balance " + availableStocks);
		}
	}

	public int getStartAmount() {
		return startAmount;
	}

}
