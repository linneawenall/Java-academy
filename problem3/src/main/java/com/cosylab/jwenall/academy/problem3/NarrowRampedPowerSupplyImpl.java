package com.cosylab.jwenall.academy.problem3;

public class NarrowRampedPowerSupplyImpl extends NarrowPowerSupplyImpl implements DeviceNarrow {
	
	public NarrowRampedPowerSupplyImpl(RampedPowerSupplyImpl rps){
		super(ps);//needs fixing
	}

	@Override
	public Object execute(String command, Object[] params) {
		switch (command) {
		case "on":
			ps.on();
			return true;
		case "off":
			ps.off();
			return true;
		case "reset":
			ps.reset();
			return true;
		case "get":
			return ps.get();
		case "set": // needs exception if params[0] doesn't contain double
			if ((Double) params[0] == 0.0) {
				ps.off();
			} else {
				ps.set((Double) params[0]);
			}
			return true;
		default:
			throw new IllegalArgumentException("There is no such command for this device");
		}

	}



}
