package com.excilys.cdb.dao;

import java.util.List;
import java.util.Optional;

import org.junit.Test;

import com.excilys.cdb.model.Company;

import junit.framework.TestCase;


public class TestCompany extends TestCase {

	public TestCompany(String testName) {
		super(testName);
	}

	@Test
	public void testFind() {
		Optional<Company> testNull = DAOFactory.getInstance().getCompanyDAO().find(0);
		assertFalse(testNull.isPresent());
		
		Optional<Company> testNotNull = DAOFactory.getInstance().getCompanyDAO().find(5);
		assertTrue(testNotNull.isPresent());
		assertEquals(5,testNotNull.get().getId());
		assertEquals("Tandy Corporation",testNotNull.get().getName());
	}

	@Test
	public void testList() {
		List<Company> testList = DAOFactory.getInstance().getCompanyDAO().list();
		assertNotNull(testList);
		assertEquals(42,testList.size());
		assertTrue(testList.get(23).getId()<testList.get(24).getId());
	}
	
}
