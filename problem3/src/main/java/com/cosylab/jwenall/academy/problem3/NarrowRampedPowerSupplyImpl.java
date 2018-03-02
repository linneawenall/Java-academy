package com.cosylab.jwenall.academy.problem3;

public class NarrowRampedPowerSupplyImpl extends NarrowPowerSupplyImpl implements DeviceNarrow {
	protected RampedPowerSupplyImpl rps;

	public NarrowRampedPowerSupplyImpl(RampedPowerSupplyImpl rps) {
		super(rps);// this is probably not right as rps might be null
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
