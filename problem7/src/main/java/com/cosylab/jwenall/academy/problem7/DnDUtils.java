package com.cosylab.jwenall.academy.problem7;

import java.awt.dnd.DnDConstants;

class DnDUtils {
	// The getProperty method returns a string containing the value of the
	// property. If the property does not exist, this version of getProperty
	// returns null.
	private static boolean debugEnabled = (System.getProperty("DnDExamples.debug") != null);

	//   Ctrl + Shift -> ACTION_LINK
	//   Ctrl         -> ACTION_COPY
	//   Shift        -> ACTION_MOVE
	public static String showActions(int action) {
		String actions = "";
		if ((action & (DnDConstants.ACTION_LINK | DnDConstants.ACTION_COPY_OR_MOVE)) == 0) {
			return "None";
		}

		if ((action & DnDConstants.ACTION_COPY) != 0) {
			actions += "Copy ";
		}

		if ((action & DnDConstants.ACTION_MOVE) != 0) {
			actions += "Move ";
		}

		if ((action & DnDConstants.ACTION_LINK) != 0) {
			actions += "Link";
		}

		return actions;
	}

	public static boolean isDebugEnabled() {
		return debugEnabled;
	}

	public static void debugPrintln(String s) {
		if (debugEnabled) {
			System.out.println(s);
		}
	}

}
