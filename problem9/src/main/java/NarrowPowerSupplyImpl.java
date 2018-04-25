
public class NarrowPowerSupplyImpl implements DeviceNarrow {// is a wrapper cuz
															// wraps around
															// PowerSupplyImpl
	protected PowerSupplyImpl ps;

	public NarrowPowerSupplyImpl(PowerSupplyImpl ps) {
		this.ps = ps;

	}

	@Override
	public Object execute(String command, String[] params) {
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
			if (isDouble(params[0])) {
				if (Double.parseDouble(params[0]) == 0.0) {
					ps.off();
				} else {
					ps.set(Double.parseDouble( params[0]));
				}
			} else {
				throw new NumberFormatException("Only numbers accepted");
			}
			return true;
		default:
			throw new IllegalArgumentException("There is no such command for this device");
		}

	}

	@Override
	public boolean isOn() {
		return ps.isOn();
	}

	@Override
	public boolean isRamping() {
		return false;
	}

	private boolean isDouble(String number) throws NumberFormatException {
		try {
			Double.parseDouble(number);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

}
