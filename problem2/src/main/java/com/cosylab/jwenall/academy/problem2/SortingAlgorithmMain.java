package com.cosylab.jwenall.academy.problem2;

import java.io.FileNotFoundException;

public class SortingAlgorithmMain {
	private static SortingAlgorithm sort;
	
    	public static void main(String[] args) throws FileNotFoundException {
    		sort = new SortingAlgorithm();
    		
    		sort.readInput(args);
    		sort.sortList();
    		sort.printList();
    	}
    }

