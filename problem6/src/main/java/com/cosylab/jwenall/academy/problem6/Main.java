package com.cosylab.jwenall.academy.problem6;

public class Main {
	public static void main(String[] args) throws InterruptedException {
	    // REVIEW (low): it would be somewhat prettier to use the for loop here, instead of copy-pasting the creation
        // and starting of Broker objects.
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
