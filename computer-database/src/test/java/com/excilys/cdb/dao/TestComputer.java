package com.excilys.cdb.dao;

import com.excilys.cdb.model.Computer;

import java.sql.Date;

import junit.framework.TestCase;

import org.junit.Test;

public class TestComputer extends TestCase {

	public TestComputer(String testName) {
		super(testName);
	}

	@Test
	public void testFind() {
		Computer testNull = DAOFactory.getInstance().getComputerDAO().find(0);
		assertNull(testNull);

		Computer testNotNull = DAOFactory.getInstance().getComputerDAO().find(5);
		assertNotNull(testNotNull);
		assertEquals(5, testNotNull.getId());
		assertEquals("CM-5", testNotNull.getName());
		assertEquals(Date.valueOf("1991-01-01"), testNotNull.getIntroduced());
		assertNull(testNotNull.getDiscontinued());
	}
}
