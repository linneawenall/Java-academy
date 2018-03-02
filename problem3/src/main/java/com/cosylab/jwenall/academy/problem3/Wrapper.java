package com.cosylab.jwenall.academy.problem3;

/* 1. Wrapper should be a class with a constructor receiving
 *its device instance as construction parameter. 
 *The wrapper's interface is the desired 'narrow' one
 * 2. The method invocation of the wrapped device is done by
 * the command parser that is a part of the wrapper. QUESTION: What is the command parser?
 * 4. Design the wrappers in such a way that would ease 
 * adding more devices to the collection, as there is 
 * a variety of devices that real-world circuits are 
 * made of, as illustrated by the Figure 1.*/
public class Wrapper implements DeviceNarrow {
	protected PowerSupplyImpl ps;
	protected RampedPowerSupplyImpl rps;
	protected NarrowPowerSupplyImpl nps;
	protected NarrowRampedPowerSupplyImpl rnps;
	
	//param device instance - device datamängd. probs not right here. look up
	public Wrapper (Object[] params){
		ps = new PowerSupplyImpl();
		rps = new RampedPowerSupplyImpl();
		nps= new NarrowPowerSupplyImpl(ps);
		rnps = new NarrowRampedPowerSupplyImpl(rps); 
	}

	@Override
	public boolean execute(String command, Object[] params) {
		commandParser(command, params);
		return false;
	}

	// the 'narrow' device's command parser will execute only sensible commands
	// by invoking methods of the wrapped device and raising an exception if the
	// command is not recognized

	// En parser är ett datorprogram (eller
	// komponent) som analyserar en dataström,
	// för att få fram en tolkning av denna i en
	// viss formell grammatik. En parser kan
	// också kallas tolk på svenska.
	// Parserkomponenter används inom många
	// datorprogram där någon indata behöver
	// tolkas, exempelvis för kommandoinmatning
	// eller inläsning av datafiler.
	private void commandParser(String command, Object[] params) {
		if (command.equalsIgnoreCase("on") || command.equalsIgnoreCase("off") || command.equalsIgnoreCase("reset")
				|| command.equalsIgnoreCase("get") || command.equalsIgnoreCase("set")) {
			if (params.getClass().getName().equals("NarrowPowerSupplyImpl")) {
				nps.execute(command, params);
			} else {
				rnps.execute(command, params);
			}
		} else if (command.equalsIgnoreCase("loadRamp") || command.equalsIgnoreCase("startRamp")) {
			rnps.execute(command, params);
		} else {
			throw new IllegalArgumentException("There is no such command");
		}
	}
}
