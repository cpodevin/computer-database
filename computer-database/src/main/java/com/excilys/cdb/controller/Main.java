package com.excilys.cdb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dao.DAOFactory;


public class Main {
	
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	public static void main(String ... args) {
		
		//logger.warn("Warning message");
		//logger.error("Error message");
		DAOFactory.getInstance();
		Controller controller = new Controller();
		controller.run();
		
	}
}
