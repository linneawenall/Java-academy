package com.cosylab.jwenall.academy.problem3;

public class Main {
	public static void main(String[] args) throws InterruptedException { 
		Circuit circ = new Circuit();
		RampedPowerSupplyImpl rps = new RampedPowerSupplyImpl();
		NarrowRampedPowerSupplyImpl rps1 = new NarrowRampedPowerSupplyImpl(rps);
		circ.add(rps1, "rps1");

		circ.execute("rps1", "on", new Object[] {});
		circ.execute("rps1", "current_set", new Object[] {5.0});
		Double current = (double)circ.execute("rps1", "current_get", new Object[] {});
		System.out.println("Current is: "+current);
		circ.execute("rps1", "reset", new Object[] {});
		Double resetCurrent = (double)circ.execute("rps1", "current_get", new Object[] {});
		System.out.println("Current is: "+resetCurrent);
		circ.execute("rps1", "loadRamp", new Object[]{ 10.2, 12.3, 13.5, 15.1, 18.4, 18.5, 20.5 });
		circ.execute("rps1", "startRamp", new Object[] { new Integer(1000) });
		Thread.sleep(10000);
		//This will also generate an IllegalThreadStateException as I'm starting the thread twice
		circ.execute("rps1", "startRamp",new Object[] { new Integer(1000) });
		
		
	}
}
