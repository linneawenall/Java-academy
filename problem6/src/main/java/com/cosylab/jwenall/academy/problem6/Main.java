package com.cosylab.jwenall.academy.problem6;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		HitStock hitStock = new HitStock();
		Broker b1 = new Broker(hitStock);
		Broker b2 = new Broker(hitStock);
		Broker b3 = new Broker(hitStock);
		Broker b4 = new Broker(hitStock);
		Broker b5 = new Broker(hitStock);
		b1.start();
		b2.start();
		b3.start();
		b4.start();
		b5.start();

	}
}
