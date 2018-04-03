package com.cosylab.jwenall.academy.problem6;

public class Main {
    public static void main(String[] args) {
        HitStock hitStock = new HitStock();
        Broker b1 = new Broker(hitStock);
        Broker b2 = new Broker(hitStock);
        Broker b3 = new Broker(hitStock);
        Broker b4 = new Broker(hitStock);
        Broker b5 = new Broker(hitStock);
//        Thread b1 = new Thread (r1);
//        Thread b2 = new Thread (r2);
//        Thread b3 = new Thread (r3);
//        Thread b4 = new Thread (r4);
//        Thread b5 = new Thread (r5);
        b1.start();
        b2.start();
        b3.start();
        b4.start();
        b5.start();
        
    }
}
