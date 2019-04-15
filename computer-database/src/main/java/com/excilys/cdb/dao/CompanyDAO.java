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
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.model.Company;

public class CompanyDAO {
	
	public CompanyDAO() { }

	private static final Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

	private final String findQuery = "SELECT id,name FROM company WHERE id = ?";
	private final String listQuery = "SELECT id,name FROM company";
	private final String deleteQuery = "DELETE FROM company WHERE id = ?";
	private final String deleteComputersQuery = "DELETE FROM computer WHERE company_id = ?";
	
	public void create(Company company) { }
	
	DriverManagerDataSource dataSource;
	
	public DriverManagerDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DriverManagerDataSource dataSource) {
		this.dataSource = dataSource;
	}

	
	public void delete(Company company) throws DAOException {
		try (Connection conn = dataSource.getConnection();
				PreparedStatement statementA = conn.prepareStatement(deleteComputersQuery);
				PreparedStatement statementB = conn.prepareStatement(deleteQuery)) {
			conn.setAutoCommit(false);
			statementA.setInt(1, company.getId());
			statementA.executeUpdate();
			
			statementB.setInt(1, company.getId());
			if (statementB.executeUpdate()!=1) {
				throw new DAOException("No line found to delete.");
			}
			conn.commit();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	public void update(Company company) { }
	
	public Optional<Company> find(int id) {
		try (Connection conn = dataSource.getConnection(); 
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
		
		try (Connection conn = dataSource.getConnection(); 
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
