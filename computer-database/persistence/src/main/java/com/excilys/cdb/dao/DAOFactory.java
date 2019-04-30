package com.excilys.cdb.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAOFactory {

	private static final Logger logger = LoggerFactory.getLogger(DAOFactory.class);

	private static DAOFactory myInstance = null;
	
	private static final String propertiesFile = "src/main/resources/properties/dao.properties";

	private String url = "jdbc:mysql://127.0.0.1:3306/computer-database-db?serverTimezone=UTC";
	private String username = "admincdb";
	private String password = "qwerty1234";
	private String driver = "com.mysql.jdbc.Driver";

	private Properties prop = new Properties();

	private DAOFactory() {

		try (InputStream input = new FileInputStream(propertiesFile)) {
			prop.load(input);
			url = prop.getProperty("url");
			username = prop.getProperty("username");
			password = prop.getProperty("password");
		} catch (IOException e) {
			logger.error("Error while loading properties file : ", e);
		}
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			logger.error("Driver not found : ", e);
		}
	}

	public static DAOFactory getInstance() {
		if (myInstance == null) {
			myInstance = new DAOFactory();
		}
		return myInstance;
	}

	public Connection getConn() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}

	public CompanyDAO getCompanyDAO() {
		return new CompanyDAO();
	}
	
	public ComputerDAO getComputerDAO() {
		return new ComputerDAO();
	}
	
}
