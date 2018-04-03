package com.cosylab.jwenall.academy.problem6;

public class Main {
    public static void main(String[] args) {
        //HitStock hitStock = new HitStock();
        Broker r1 = new Broker();
        Broker r2 = new Broker();
        Broker r3 = new Broker();
        Broker r4 = new Broker();
        Broker r5 = new Broker();
        Thread b1 = new Thread (r1);
        Thread b2 = new Thread (r2);
        Thread b3 = new Thread (r3);
        Thread b4 = new Thread (r4);
        Thread b5 = new Thread (r5);
        b1.start();
        b2.start();
        b3.start();
        b4.start();
        b5.start();
        
    }
}
