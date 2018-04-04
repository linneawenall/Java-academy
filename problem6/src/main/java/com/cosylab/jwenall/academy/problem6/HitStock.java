package com.cosylab.jwenall.academy.problem6;

import java.util.Scanner;

//This class holds records of how many stocks are available on the market only for our "Hit" stock. For buying and selling we will have corresponding class methods. One stock will suffice for our objectives.

// REVIEW (high): the assignment instructions require this class to be implemented as a singleton class.
// For detailed info on singleton classes, you can read, for example, the page below:
// https://www.javaworld.com/article/2073352/core-java/simply-singleton.html

//The HitStock class gathers requests from various brokers, which all run in their separate threads. 
public class HitStock {
	// By declaring the counter variable volatile all writes to the counter
	// variable will be written back to main memory immediately. Also, all reads
	// of the counter variable will be read directly from main memory
	// private volatile Thread brokerThread;

	private volatile int availableStocks;
	private int startAmount; // stock opening balance should be read from consol
								// input

	// REVIEW (high): for singleton objects, the constructor should be made private.
	public HitStock() {
		// REVIEW (medium): It would be better if the HitStock constructor would get the start amount as an argument.
		// The code for reading and parsing the argument from the standard input should be moved to the "main" method.
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
			// REVIEW (medium): you don't need to throw exception here. Not executing the buy is enough.
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
