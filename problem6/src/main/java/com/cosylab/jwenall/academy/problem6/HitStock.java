package com.cosylab.jwenall.academy.problem6;

import java.util.Scanner;

//This class holds records of how many stocks are available on the market only for our "Hit" stock. For buying and selling we will have corresponding class methods. One stock will suffice for our objectives.

//The HitStock class gathers requests from various brokers, which all run in their separate threads. 
public class HitStock {
	// By declaring the counter variable volatile all writes to the counter
	// variable will be written back to main memory immediately. Also, all reads
	// of the counter variable will be read directly from main memory
	// private volatile Thread brokerThread;

	private volatile int availableStocks;
	private int startAmount; // stock opening balance should be read from consol
								// input

	public HitStock() {
		System.out.println("Write the amount of stocks to start with.");
		Scanner startStocks = new Scanner(System.in);
		startAmount = Integer.parseInt(startStocks.nextLine());
		startStocks.close();
		// synchronized(X.class) is used to make sure that there is exactly one
		// Thread in the block.
		synchronized (HitStock.class) {
			Stock.getInstance();
			availableStocks = startAmount;
			System.out.println("Stock opening balance: " + startAmount);
		}
	}

	public synchronized void buy(int stockAmount) {
		if (availableStocks >= stockAmount) {
			System.out.println("BUY of " + stockAmount + " by " + Thread.currentThread());
			availableStocks = availableStocks - stockAmount;
			System.out.println("New Balance: " + availableStocks);
		} else {
			System.out.println("NOT ENOUGH STOCKS.");
			throw new IllegalStateException();

		}
	}

	public synchronized void sell(int stockAmount) {
		System.out.println("SELL of " + stockAmount + " by " + Thread.currentThread());
		availableStocks = availableStocks + stockAmount;
		System.out.println("New Balance: " + availableStocks);
	}

	public int getStartAmount() {
		return startAmount;
	}

}
