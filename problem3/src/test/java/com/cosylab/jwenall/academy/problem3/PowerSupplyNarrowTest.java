package com.cosylab.jwenall.academy.problem3;

import junit.framework.TestCase;

public class PowerSupplyNarrowTest extends TestCase {

	 public void testCommandBasics() {
	 Boolean succes;
	 Circuit circ = new Circuit();
	 PowerSupplyImpl ps = new PowerSupplyImpl();
	 NarrowPowerSupplyImpl ps1 = new NarrowPowerSupplyImpl(ps);
	 circ.add(ps1, "ps1");
	
	 /*
	 * It is still possible to use the power supply device. Just an
	 * illustration where to get the power supply device, but here it is not
	 * intended to be used this way.
	 */
	 ps.on();
	 boolean deviceSucces = ps.power;
	 assertTrue("device_on", (deviceSucces));
	
	 /*
	 * The wrapped device is already turned on. Just an ilustration of the
	 * wrapper interface, but here it is not intended to be used this way.
	 */
	 succes = (Boolean) ps1.execute("reset", new Object[] {});
	 assertTrue("narrow_reset", (succes.booleanValue()));
	
	 /* Set the current over the control request. */
	 succes = (Boolean) circ.execute("ps1", "current_set", new Object[] { new
	 Double(0.5) });
	 assertTrue("currentSet", (succes.booleanValue()));
	
	 /* Returned current value must match the previously set one. */
	 Double current = (Double) circ.execute("ps1", "current_get", new Object[]
	 {});
	 assertTrue("currentGet", (current.equals(new Double(0.5))));
	
	 /*
	 * We conclude our interface presentation with turning the device off.
	 */
	 succes = (Boolean) circ.execute("ps1", "off", new Object[] {});
	 assertTrue("circDevice_off", (succes.booleanValue()));
	 }


	public void testCommandsforRamped() {
		Boolean succes;
		Circuit circ = new Circuit();
		RampedPowerSupplyImpl rps = new RampedPowerSupplyImpl();
		NarrowRampedPowerSupplyImpl rps1 = new NarrowRampedPowerSupplyImpl(rps);
		circ.add(rps1, "rps1");

		System.out.println("Turning rps power on");
		rps.on();
		boolean deviceSucces = rps.power;
		assertTrue("device_on", (deviceSucces));

		System.out.println("Trying to reset rps1");
		succes = (Boolean) rps1.execute("reset", new Object[] {});
		assertTrue("narrow_reset", (succes.booleanValue()));

		succes = (Boolean) circ.execute("rps1", "current_set", new Object[] { new Double(0.5) });
		assertTrue("currentSet", (succes.booleanValue()));

		Double current = (Double) circ.execute("rps1", "current_get", new Object[] {});
		assertTrue("currentGet", (current.equals(new Double(0.5))));

		/* Test loadRamp */
		succes = (Boolean) circ.execute("rps1", "loadRamp", new Object[] { 10.2, 12.3, 13.5, 15.1, 18.4, 18.5, 20.5 });
		assertTrue("loadRamp", succes.booleanValue());

		/*
		 * Test startRamp. This one doesnt work properly. throws exception for
		 * PowerSupplyImpl.java.53
		 */
		succes = (Boolean) circ.execute("rps1", "startRamp", new Object[] { new Integer(1000) });
		assertTrue("startRamp", succes.booleanValue());

		// /* Can't call commands when power is turned off */
		// succes = (Boolean) circ.execute("rps1", "off", new Object[] {});
		// assertTrue("circDevice_off", (succes.booleanValue()));

		// try {
		// circ.execute("rps1", "current_set", new Object[] { new Double(0.5)
		// });
		// } catch (IllegalStateException e) {
		// fail("Exception when trying to set current while power is off" + e);
		// }

		// try {
		// circ.execute("rps1", "current_get", new Object[] {});
		// } catch (IllegalStateException e) {
		// fail("Exception when trying to get current while power is off" + e);
		// }
	}
}
