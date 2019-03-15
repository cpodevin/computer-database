package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DAOConnection {
	
	private final static DAOConnection myInstance = new DAOConnection();
	
	private final String url = "jdbc:mysql://127.0.0.1:3306/computer-database-db";
	private final String login = "admincdb";
	private final String password = "qwerty1234";
	
	private DAOConnection() {
			
	}
	
	public static DAOConnection getInstance() {
		return myInstance;
	}
	
	public Connection getConn() throws SQLException {
		return DriverManager.getConnection(url, login, password);
	}

	
}
