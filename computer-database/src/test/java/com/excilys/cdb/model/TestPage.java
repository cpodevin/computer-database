package com.excilys.cdb.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import junit.framework.TestCase;

public class TestPage extends TestCase {

	List<Integer> list;
	Page<Integer> page1;
	Page<Integer> page2;

	public TestPage(String testName) {
		super(testName);
		list = new ArrayList<>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(5);
		list.add(6);
		page1 = new Page<>(list);
		page2 = new Page<>(list, 2);
	}

	@Test
	public void testValues() {
		assertEquals(0, page1.getIndex());
		assertEquals(5, page1.getNbLine());
		assertEquals(30, page1.getPageSize());

		assertEquals(0, page2.getIndex());
		assertEquals(5, page2.getNbLine());
		assertEquals(2, page2.getPageSize());

	}

	@Test
	public void testBehavior() {
		assertEquals(list, page1.getPage());
		assertFalse(page1.next());
		assertEquals(list, page1.getPage());
		assertFalse(page1.previous());
		assertEquals(list, page1.getPage());
		
		assertEquals(list, page2.getData());
		assertFalse(page2.previous());
		assertEquals(0, page2.getIndex());
		assertTrue(page2.next());
		assertEquals(2, page2.getIndex());
		assertTrue(page2.next());
		assertEquals(1, page2.getPage().size());
		assertFalse(page2.next());
	}

}
