package com.cosylab.jwenall.academy.problem6;

//This class holds records of how many stocks are available on the market only for our "Hit" stock. For buying and selling we will have corresponding class methods. One stock will suffice for our objectives.

//The HitStock class gathers requests from various brokers, which all run in their separate threads. 
public class HitStock {
	private int availableStocks;

	public void buy(int stockAmount) {
		if (stockAmount > availableStocks) {
			System.out.println("Not enough stocks available.");
			throw new IllegalStateException();
		} else {
			System.out.println("BUY of" + stockAmount + " by Thread" + Thread.currentThread());
			availableStocks = availableStocks - stockAmount;
			System.out.println("New Balance" + availableStocks);
		}
	}

	public void sell(int stockAmount) {
		availableStocks = availableStocks + stockAmount;
		System.out.println("SELL of" + stockAmount + "by Thread" + Thread.currentThread());
		System.out.println("New Balance" + availableStocks);
	}

}
