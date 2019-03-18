package com.excilys.cdb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {
	
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	public static void main(String ... args) {
		
		logger.warn("Warning message");
		logger.debug("Error message");
		
		Controller controller = new Controller();
		controller.run();
		
	}
}
