package com.cosylab.jwenall.academy.problem8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Sorter implements Comparator<Number> {
	private int order;
	private ArrayList<Number> input;

	public Sorter() {
		this.input = new ArrayList<Number>();

	}

	public ArrayList<Number> sort(int order, ArrayList<Number> input) {
		this.input = input;
		this.order = order;
		sortList();
		System.out.println("Sorting done: " + input.toString());
		return input;
	}

	/* Uses Collections to sort */
	public void sortList() throws IllegalArgumentException {
		if (!input.isEmpty()) {
			Collections.sort(input, this);
		} else
			throw new IllegalArgumentException("ERROR: List is empty");
	}

	/* Overrides compare method in Comparator. */
	@Override
	public int compare(Number n1, Number n2) {
		if (n1.doubleValue() < n2.doubleValue()) {
			return order * -1;
		}
		if (n1.doubleValue() > n2.doubleValue()) {
			return order * 1;
		}
		return 0;
	}

}
