package com.cosylab.jwenall.academy.problem8;

import java.util.Date;

public class Document {
	// Document's attributes.
	private String projectName;
	private String date;
	private int docNumber;
	private String suffix;
	private String path;

	public Document(String projectName, String date, int docNumber, String suffix, String path) {
		this.projectName = projectName;
		this.date = date;
		this.docNumber = docNumber;
		this.suffix = suffix;
		this.path = path;

	}

	// @return the projectName
	public String getProjectName() {
		return projectName;
	}

	// @return the date
	public String getDate() {
		return date;
	}

	// @return the docNumber
	public int getDocNumber() {
		return docNumber;
	}

	// @return the suffix
	public String getSuffix() {
		return suffix;
	}

	// @return the path
	public String getPath() {
		return path;
	}
}
