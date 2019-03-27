package com.excilys.cdb.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class DAOException extends Exception {
	
	private static final Logger logger = LoggerFactory.getLogger(DAOException.class);
	
	public DAOException(String message) {
		super(message);
		logger.warn("DB Error : " + message);
	}
	
	public DAOException(Exception e) {
		super(e);
		logger.warn("DB Error : ", e);
	}

}
