package com.cosylab.jwenall.academy.problem4;

public class NarrowPowerSupplyImpl implements DeviceNarrow {//is a wrapper cuz wraps around PowerSupplyImpl
	protected PowerSupplyImpl ps;

	public NarrowPowerSupplyImpl(PowerSupplyImpl ps) {
		this.ps = ps;
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
		case "current_get":
			return ps.get();
		case "current_set": 
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
