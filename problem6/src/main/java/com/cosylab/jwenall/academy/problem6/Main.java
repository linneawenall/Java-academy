package com.cosylab.jwenall.academy.problem6;

import java.util.ArrayList;
import java.util.Scanner;

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
		}
		
		for(Broker b: brokers){
			b.join(1000);
			b.stopTrading();
		}
	}
}
