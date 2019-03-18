package com.excilys.cdb.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Company;

public class CompanyDAO extends DAO<Company> {

	private final static CompanyDAO myInstance = new CompanyDAO(DAOConnection.getInstance());
	private static final Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	
	private final String findQuery = "SELECT * FROM company WHERE id = ?";
	private final String listQuery = "SELECT * FROM company";
	
	private CompanyDAO(DAOConnection conn) {
		super(conn);
	}
	
	public static CompanyDAO getInstance() {
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
	
	public Company find(int id) {
		try (PreparedStatement statement = conn.getConn().prepareStatement(findQuery)) {			
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				return new Company(result.getInt("id"),result.getString("name"));
			}
		} catch (SQLException e) {
			logger.error("DB Error", e);
		}
		return null;
	}
	
	public List<Company> list() {
		List<Company> resList = new ArrayList<>();
		
		try (PreparedStatement statement = conn.getConn().prepareStatement(listQuery)) {			
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
