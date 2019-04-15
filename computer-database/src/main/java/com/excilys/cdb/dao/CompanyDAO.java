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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.excilys.cdb.dto.CompanyMapper;
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
	
	private JdbcTemplate jdbcTemplate;
	
	private CompanyMapper companyMapper;
	
	public CompanyMapper getCompanyMapper() {
		return companyMapper;
	}

	public void setCompanyMapper(CompanyMapper companyMapper) {
		this.companyMapper = companyMapper;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

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
		return Optional.ofNullable(jdbcTemplate.queryForObject(findQuery, companyMapper));
	}
	
	public List<Company> list() {
		return jdbcTemplate.query(listQuery, companyMapper);
	}
	
}
