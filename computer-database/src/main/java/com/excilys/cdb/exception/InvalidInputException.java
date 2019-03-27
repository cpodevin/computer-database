package com.excilys.cdb.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvalidInputException extends Exception{

	private static final Logger logger = LoggerFactory.getLogger(InvalidInputException.class);
	
	public InvalidInputException(String message) {
		super(message);
		logger.warn("DB Error : " + message);
	}

}
