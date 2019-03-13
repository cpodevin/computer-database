package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DAOConnection {
	
	private final static DAOConnection myInstance = new DAOConnection();
	
	private Connection conn;
	
	
	private DAOConnection() {
		String url = "jdbc:mysql://127.0.0.1:3306/computer-database-db";
		String login = "admincdb";
		String password = "qwerty1234";
		try {
			conn = DriverManager.getConnection(url, login, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public static DAOConnection getInstance() {
		return myInstance;
	}
	
	public static Connection getConn() {
		return myInstance.conn;
	}

	public void finalize() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
