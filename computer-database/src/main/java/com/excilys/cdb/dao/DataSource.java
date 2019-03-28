package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataSource {
	private static HikariDataSource ds;
	private static HikariConfig config = new HikariConfig(/*"src/main/resources/properties/hikari.properties"*/);
	
	static {
		config.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/computer-database-db");
		config.setUsername("admincdb");
		config.setPassword("qwerty1234");
		config.setConnectionTimeout(4000);
		config.setAutoCommit(false);
		ds =  new HikariDataSource(config);
	}

	private DataSource() { }
	
	public static Connection getConn() throws SQLException {
        return ds.getConnection();
	}
}
