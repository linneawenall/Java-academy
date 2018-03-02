package com.cosylab.jwenall.academy.problem3;

public class NarrowPowerSupplyImpl implements DeviceNarrow {
	protected PowerSupplyImpl ps;

	public NarrowPowerSupplyImpl(PowerSupplyImpl ps) {
		// REVIEW (high): you should remove the line below. This one will override the "ps" that you are passing via argument.
		ps = new PowerSupplyImpl();
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
