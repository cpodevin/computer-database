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
	
	public Connection getConn() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, login, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	
}
