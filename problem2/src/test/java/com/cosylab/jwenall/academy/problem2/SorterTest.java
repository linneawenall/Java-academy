package com.cosylab.jwenall.academy.problem2;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;



import org.junit.After;
import org.junit.Before;

public class SorterTest {
	Sorter s;
	Parsing p;


	@Before
	public void setUp() throws Exception {
		p = new Parsing("A: 1,8,5,7,2.5,6.7,1.0");
		p.parse();
		s = new Sorter();
	}

	@After
	public void tearDown() throws Exception {

	}
	
	@Test
	public void testSort(){
		s.sort(p.getOrder(), p.getList());
		double twoPointFive = (double) s.sort(p.getOrder(), p.getList()).get(2);
		System.out.println(twoPointFive);
		System.out.println("sorted list: " +s.sort(p.getOrder(), p.getList()).toString() );
		assertTrue("Number at third index is 2.5",(double) s.sort(p.getOrder(), p.getList()).get(2)==2.5 );
		
	}

	@Test
	public void testSortList() {
		try {
			s.sort(p.getOrder(), p.getList());
			s.sortList();
		} catch (IllegalArgumentException exception) {
			fail("Exception when sorting with sortList method: " + exception);
		}
	}

	@Test
	public void testCompare(){
		s.sort(p.getOrder(), p.getList());
		Number n1 = 3;
		Number n2 = 3.7;
		int comp = s.compare(n1, n2);
		System.out.println(comp);
		assertTrue("Comp is -1", comp == -1);
	}
}
