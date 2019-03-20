package com.excilys.cdb.dao;

import com.excilys.cdb.model.Computer;

import java.sql.Date;
import java.util.Optional;

import junit.framework.TestCase;

import org.junit.Test;

public class TestComputer extends TestCase {

	public TestComputer(String testName) {
		super(testName);
	}

	@Test
	public void testFind() {
		Optional<Computer> testNull = DAOFactory.getInstance().getComputerDAO().find(0);
		assertFalse(testNull.isPresent());

		Optional<Computer> testNotNull = DAOFactory.getInstance().getComputerDAO().find(5);
		assertTrue(testNotNull.isPresent());
		assertEquals(5, testNotNull.get().getId());
		assertEquals("CM-5", testNotNull.get().getName());
		assertEquals(Date.valueOf("1991-01-01"), testNotNull.get().getIntroduced());
		assertNull(testNotNull.get().getDiscontinued());
	}
}
