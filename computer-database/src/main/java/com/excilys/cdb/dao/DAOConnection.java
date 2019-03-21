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


public class DAOConnection {

	private static final Logger logger = LoggerFactory.getLogger(DAOConnection.class);
	private static final DAOConnection myInstance = new DAOConnection();

	
	private static final String propertiesFile = "src/main/resources/properties/dao.properties";
	
	private String url;
	private String username;
	private String password;
	
	private Properties prop = new Properties();
	
	private DAOConnection() {
		try (InputStream input = new FileInputStream(propertiesFile)) {
			prop.load(input);
			url = prop.getProperty("url");
			username = prop.getProperty("username");
			password = prop.getProperty("password");
		} catch (IOException e) {
			logger.error("Error while loading properties file : ", e);
		}
		System.out.println(url);
	}
	
	public static DAOConnection getInstance() {
		return myInstance;
	}
	
	public Connection getConn() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}

	
}
