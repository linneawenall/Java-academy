package com.cosylab.jwenall.academy.problem3;

import com.cosylab.jwenall.academy.problem1.PowerSupply;

/* Construct the commands of the 'narrow' interface 
 * from the wrapped device's interface. These are 
 * indicated in the Tests (4.) section.*/
public interface DeviceNarrow {


	public boolean execute (String command, Object [] params);
	
}
