package com.cosylab.jwenall.academy.problem3;

// REVIEW (high): remove the import below since it's a source of compile-time errors. Also, the import is not needed
// anywhere in this file.
import com.cosylab.jwenall.academy.problem1.PowerSupply;

/* Construct the commands of the 'narrow' interface 
 * from the wrapped device's interface. These are 
 * indicated in the Tests (4.) section.*/
public interface DeviceNarrow {


	public Object execute (String command, Object [] params);
	
}
