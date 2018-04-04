package com.cosylab.jwenall.academy.problem6;


//This class holds records of how many stocks are available on the market only for our "Hit" stock. For buying and selling we will have corresponding class methods. One stock will suffice for our objectives.

//The HitStock class gathers requests from various brokers, which all run in their separate threads. 
public class HitStock {
	private static HitStock instance = null;
	private volatile static int availableStocks;
	private int startAmount;

	private HitStock(int startAmount) {
		synchronized (HitStock.class) {
			this.startAmount = startAmount;
			availableStocks = startAmount;
		}
	}

	public static HitStock getInstance(int startAmount) {

		if (instance == null) {// not thread-safe
			instance = new HitStock(startAmount);
		}
		return instance;
	}

	public synchronized static void buy(int stockAmount) {
		if (availableStocks >= stockAmount) {
			System.out.println("BUY of " + stockAmount + " by " + Thread.currentThread());
			availableStocks = availableStocks - stockAmount;
			System.out.println("New Balance: " + availableStocks);
		} else {
			System.out.println("NOT ENOUGH STOCKS.");
		}
	}

	public synchronized static void sell(int stockAmount) {
		System.out.println("SELL of " + stockAmount + " by " + Thread.currentThread());
		availableStocks = availableStocks + stockAmount;
		System.out.println("New Balance: " + availableStocks);
	}
	public int getAvailableStocks(){
		return availableStocks;
	}
	

}
