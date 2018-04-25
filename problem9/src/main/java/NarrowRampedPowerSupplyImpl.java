
public class NarrowRampedPowerSupplyImpl extends NarrowPowerSupplyImpl implements DeviceNarrow {
	private RampedPowerSupply rps;

	public NarrowRampedPowerSupplyImpl(RampedPowerSupplyImpl rps) {
		super(rps);
		this.rps = rps;
	}

	@Override
	public Object execute(String command, String[] params) throws NullPointerException, IllegalArgumentException {
		switch (command) {
		case "on":
		case "off":
		case "reset":
		case "current_get":
		case "current_set":
			return super.execute(command, params);
		case "loadRamp":
			if (params.length == 0) {
				throw new NullPointerException("Params is null");
			} else if (isAllDouble( params)) {
				double[] rampValues = new double[params.length];
				for (int i = 0; i < rampValues.length; i++) {
					rampValues[i] = Double.parseDouble(params[i]);
				}
				RampedPowerSupplyImpl rps = (RampedPowerSupplyImpl) ps;
				rps.loadRamp(rampValues);
				return true;
			} else {
				throw new NumberFormatException("Only numbers accepted as rampvalues");
			}
		case "startRamp":
			if (params[0] == null) {
				throw new IllegalArgumentException("Time argument in params is null");
			}
			if (isInteger( params[0])) {
				RampedPowerSupplyImpl rps = (RampedPowerSupplyImpl) ps;
				rps.startRamp(Integer.parseInt( params[0]));
				return true;
			} else {
				throw new NumberFormatException("Only one number accepted");
			}
		default:
			throw new IllegalArgumentException("There is no such command for this device");
		}

	}

	@Override
	public boolean isOn() {
		return super.isOn();
	}

	@Override
	public boolean isRamping() {
		return rps.isRamping();
	}

	private boolean isDouble(String number) throws NumberFormatException {
		try {
			Double.parseDouble(number);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	private boolean isAllDouble(String[] array) {
		for (int i = 0; i < array.length; i++) {
			if (!isDouble(array[i])) {
				return false;
			}
		}
		return true;
	}

	private boolean isInteger(String number) throws NumberFormatException {
		try {
			Integer.parseInt(number);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

}
