package com.excilys.cdb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.cdb.dao.DAOFactory;
import com.excilys.cdb.service.ComputerService;


public class Main {
	
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	public static void main(String ... args) {
		
		//logger.warn("Warning message");
		//logger.error("Error message");
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    	Controller controller = (Controller) context.getBean("controller");
		controller.run();
		((AbstractApplicationContext) context).close();
	}
}
