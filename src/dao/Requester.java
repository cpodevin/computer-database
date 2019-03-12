package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Requester {
	
	
	private Connection conn = null;
	private String query = null;
	private ResultSet result = null;
	
	public Requester() {
		String url = "jdbc:mysql://127.0.0.1:3306/computer-database-db";
		String login = "admincdb";
		String password = "qwerty1234";
		try {
			conn = DriverManager.getConnection(url, login, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public Connection getConn() {
		return conn;
	}
	
	
	public List<String> requestComputers() throws SQLException  {
		
		query = "SELECT * FROM company;";		
		Statement statement = conn.createStatement();
		result = statement.executeQuery(query);
		
		List<String> res = new ArrayList<>(); 
		while (result.next())  {
			res.add(result.getString("name"));
		}
		return res;
	
	}
	
	public void finalize() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
