package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Company;

public class CompanyDAO {

	private static final Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

	private static CompanyDAO myInstance = new CompanyDAO(DAOFactory.getInstance());
	private DAOFactory factory;

	private final String findQuery = "SELECT id,name FROM company WHERE id = ?";
	private final String listQuery = "SELECT id,name FROM company";

	private CompanyDAO(DAOFactory conn) {
		this.factory = conn;
	}

	public static CompanyDAO getInstance(DAOFactory conn) {
		if (myInstance == null) {
			myInstance = new CompanyDAO(conn);
		}
		return myInstance;
	}
	
	public boolean create(Company company) {
		return false;
	}
	
	public boolean delete(Company company) {
		
		return false;
	}
	
	public boolean update(Company company) {
		return false;
	}
	
	public Optional<Company> find(int id) {
		try (Connection conn = DataSource.getConn(); 
				PreparedStatement statement = conn.prepareStatement(findQuery)) {			
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				return Optional.of(new Company(result.getInt("id"),result.getString("name")));
			}
		} catch (SQLException e) {
			logger.error("DB Error", e);
		}
		return Optional.empty();
	}
	
	public List<Company> list() {
		List<Company> resList = new ArrayList<>();
		
		try (Connection conn = DataSource.getConn(); 
				PreparedStatement statement = conn.prepareStatement(listQuery)) {		
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				resList.add(new Company(result.getInt("id"),result.getString("name")));
			}
		} catch (SQLException e) {
			logger.error("DB Error", e);
		}
		return resList;
	}
	
}
