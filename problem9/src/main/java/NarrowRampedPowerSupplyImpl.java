
public class NarrowRampedPowerSupplyImpl extends NarrowPowerSupplyImpl implements DeviceNarrow {
	private RampedPowerSupply rps;

	public NarrowRampedPowerSupplyImpl(RampedPowerSupplyImpl rps) {
		super(rps);
		this.rps=rps;
	}

	@Override
	public Object execute(String command, Object[] params) throws NullPointerException, IllegalArgumentException {
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
			} else {
				double[] rampValues = new double[params.length];
				try {
					for (int i = 0; i < rampValues.length; i++) {
						rampValues[i] = (Double) params[i];
					}
				} catch (NumberFormatException e) {
				}
				RampedPowerSupplyImpl rps = (RampedPowerSupplyImpl) ps;
				rps.loadRamp(rampValues);
			}
			return true;
		case "startRamp":
			if (params[0] == null) {
				throw new IllegalArgumentException("Time argument in params is null");
			}
			RampedPowerSupplyImpl rps = (RampedPowerSupplyImpl) ps;
			rps.startRamp((int) params[0]);
			return true;
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

}
