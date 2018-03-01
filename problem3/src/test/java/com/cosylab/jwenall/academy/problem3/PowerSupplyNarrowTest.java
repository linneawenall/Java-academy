package com.cosylab.jwenall.academy.problem3;


import junit.framework.TestCase;

public class PowerSupplyNarrowTest extends TestCase {
   
   public void testCommandBasics() {
      Boolean succes;
      Circuit circ = new Circuit();
      PowerSupplyImpl ps = new PowerSupplyImpl();
      NarrowPowerSupplyImpl ps1 = new NarrowPowerSupplyImpl(ps); 
      circ.add(ps1, "ps1");
      
      
      /* It is still possible to use the power supply device.
       * Just an illustration where to get the power supply device,
       * but here it is not intended to be used this way. */      
      boolean deviceSucces = ps.on();
      assertTrue("device_on", (deviceSucces));

      /* The wrapped device is already turned on.
       * Just an ilustration of the wrapper interface,
       * but here it is not intended to be used this way. */
      succes = (Boolean) ps1.execute("reset", new Object[] {});
      assertTrue("narrow_reset", (succes.booleanValue()));

      /* Set the current over the control request. */
      succes = (Boolean) circ.execute("ps1", "current_set", new Object[] {new Double(0.5)});  
      assertTrue("currentSet", (succes.booleanValue()));

      /* Returned current value must match the previously set one. */
      Double current = (Double) circ.execute("ps1", "current_get", new Object[] {}); 
      assertTrue("currentGet", (current.equals(new Double(0.5))));

      /* We conclude our interface presentation with turning the device off. */
      succes = (Boolean) circ.execute("ps1", "off", new Object[] {});
      assertTrue("circDevice_off", (succes.booleanValue()));
   }
}
