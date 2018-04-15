package com.cosylab.jwenall.academy.problem9;

import java.io.Serializable;

//Just like the class "Student" from  StackOverFlow example
public class Command implements Serializable {

	private static final long serialVersionUID = 2334595196329179652L;
	
	private String commandName;
	private Object[] commandParameters;
	
	
	public Command(String commandName, Object[] commandParamters) {
		this.commandName = commandName;
		this.commandParameters = commandParamters;
	}
	
	public String getName() {
		return commandName;
	}
	
	public Object[] getParamters() {
		return commandParameters;
	}

}
