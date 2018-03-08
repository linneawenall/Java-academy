package com.cosylab.jwenall.academy.problem4;

import java.util.ArrayList;
import java.util.HashMap;

public class Circuit {
	HashMap<String, DeviceNarrow> hp;
	ArrayList<String> failureDevices;

	public Circuit() {
		hp = new HashMap<String, DeviceNarrow>();
		failureDevices = new ArrayList<String>();
	}

	public void add(DeviceNarrow device, String deviceName) {
		if (device == null)
			throw new NullPointerException("The device is null");
		if (deviceName.isEmpty()) {
			throw new IllegalArgumentException("The devicename is empty");
		}
		hp.put(deviceName, device);
		System.out.println("Running failureTest turning on and off");
		failureTest(deviceName);
	}

	public Object execute(String deviceName, String command, Object[] params) throws IllegalArgumentException {
		if (deviceName == null || deviceName.isEmpty()) {
			throw new NullPointerException("DeviceName is empty or null");
		} else if (!hp.containsKey(deviceName)) {
			throw new IllegalArgumentException("There is no device with this name");
		} else if (inFailureState(deviceName)) {
			throw new IllegalStateException("The device is in failure state");
		}
		System.out.println("Starting to execute for: " + deviceName);
		return hp.get(deviceName).execute(command, params);

	}

	private void failureTest(String deviceName) {
		if (!((boolean) execute(deviceName, "on", new Object[] {}))
				|| !(boolean) execute(deviceName, "off", new Object[] {})) {
			failureDevices.add(deviceName);
		}
	}

	private boolean inFailureState(String deviceName) {
		return failureDevices.contains(deviceName);
	}
}