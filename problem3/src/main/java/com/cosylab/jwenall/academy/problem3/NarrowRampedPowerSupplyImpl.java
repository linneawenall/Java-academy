package com.cosylab.jwenall.academy.problem3;

public class NarrowRampedPowerSupplyImpl extends NarrowPowerSupplyImpl implements DeviceNarrow {
	protected RampedPowerSupplyImpl rps;

	public NarrowRampedPowerSupplyImpl(RampedPowerSupplyImpl rps) {
		// REVIEW (medium): you actually don't need to call the super constructor, so the code below can be rmeoved.
		super(rps);// this is probably not right as rps might be null
		// REVIEW (high): you should remove the line below. This one will override the "rps" that you are passing via argument.
		rps = new RampedPowerSupplyImpl();
		this.rps = rps;
	}

	@Override
	public Object execute(String command, Object[] params) {
		switch (command) {
		case "on":
			rps.on();
			return true;
		case "off":
			rps.off();
			return true;
		case "reset":
			rps.reset();
			return true;
		case "get":
			return rps.get();
		case "set": // needs exception if params[0] doesn't contain double
			if ((Double) params[0] == 0.0) {
				rps.off();
			} else {
				rps.set((Double) params[0]);
			}
			return true;
		case "loadRamp":
			// REVIEW (high): here you are currently only passing the first ramp element to "rps.loadRamp". You need
			// to pass all the ramp values to "rps.loadRamp".
			rps.loadRamp((double[]) params[0]);
			return true;
		case "startRamp":
			rps.startRamp((int) params[0]);
			return true;
		default:
			throw new IllegalArgumentException("There is no such command for this device");
		}

	}

}
