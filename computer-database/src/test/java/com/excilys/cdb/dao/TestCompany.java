package com.excilys.cdb.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.excilys.cdb.model.Company;

import junit.framework.TestCase;


public class TestCompany extends TestCase {

	public TestCompany(String testName) {
		super(testName);
	}

	@Test
	public void testFind() {
		Company testNull = DAOFactory.getInstance().getCompanyDAO().find(0);
		assertNull(testNull);
		
		Company testNotNull = DAOFactory.getInstance().getCompanyDAO().find(5);
		assertNotNull(testNotNull);
		assertEquals(5,testNotNull.getId());
		assertEquals("Tandy Corporation",testNotNull.getName());
	}

	@Test
	public void testList() {
		List<Company> testList = DAOFactory.getInstance().getCompanyDAO().list();
		assertNotNull(testList);
		assertEquals(42,testList.size());
		assertTrue(testList.get(23).getId()<testList.get(24).getId());
	}
	
}
