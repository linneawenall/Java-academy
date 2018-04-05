package com.cosylab.jwenall.academy.problem6;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.swing.Timer;

public class Main {
	private static int startAmount;

	public static void main(String[] args) throws InterruptedException {

		System.out.println("Write the amount of stocks to start with.");
		Scanner startStocks = new Scanner(System.in);
		startAmount = Integer.parseInt(startStocks.nextLine());
		startStocks.close();
		System.out.println("Stock opening balance: " + startAmount);
		HitStock hitStock = HitStock.getInstance(startAmount);

		ArrayList<Broker> brokers = new ArrayList<Broker>();

		for (int i = 0; i < 5; i++) {
			brokers.add(new Broker(startAmount));
			brokers.get(i).start();
			System.out.println("Starting");
		}
		
		//TimeUnit.SECONDS.sleep(5);
		Thread.sleep(1000);
		System.out.println("After sleep");
	
		// REVIEW (medium): here, it would make sense sleeping for a minute or so, so some trading is performed.
		
		for (Broker broker : brokers) {
			broker.stopTrading();
		}

		
		for (Broker broker : brokers) {
			try {
				broker.join();
			} catch (InterruptedException e) {
				System.out.println("Simulation has been interrupted. Exception: " + e.getMessage());
			}
		}

		System.out.println("\nHitStock closes with " + hitStock.getAvailableStocks() + " available stocks.");
		System.out.println("Stock exchange simulation finished.");
	}
}
