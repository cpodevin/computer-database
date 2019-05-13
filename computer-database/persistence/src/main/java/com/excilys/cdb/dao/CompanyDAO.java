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
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.QCompany;
import com.excilys.cdb.model.QComputer;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class CompanyDAO {
	
	public CompanyDAO() { }

	private static final Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

	private final String findQuery = "SELECT id,name FROM company WHERE id = ?";
	private final String listQuery = "SELECT id,name FROM company";
	private final String deleteQuery = "DELETE FROM company WHERE id = ?";
	private final String deleteComputersQuery = "DELETE FROM computer WHERE company_id = ?";
	

	
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

	private JPAQueryFactory queryFactory;

	public JPAQueryFactory getQueryFactory() {
		return queryFactory;
	}

	public void setQueryFactory(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	public void create(Company company) { }
	
	public void delete(Company company) throws DAOException {
		try (Connection conn = dataSource.getConnection();
				PreparedStatement statementA = conn.prepareStatement(deleteComputersQuery);
				PreparedStatement statementB = conn.prepareStatement(deleteQuery)) {
			conn.setAutoCommit(false);
			statementA.setInt(1, company.getId());
			statementA.executeUpdate();
			
			statementB.setInt(1, company.getId());
			if (statementB.executeUpdate()!=1) {
				logger.warn("Delete Company : No line found to delete.");
				throw new DAOException("No line found to delete.");
			}
			conn.commit();
		} catch (SQLException e) {
			logger.warn("DB error : ", e);
			throw new DAOException(e);
		}
	}
	
	public void update(Company company) { }
	
	public Optional<Company> find(int id) {
//		try {
//			return Optional.of(jdbcTemplate.queryForObject(findQuery, companyMapper));
//		} catch (IncorrectResultSizeDataAccessException e) {
//			return Optional.empty();
//		}
		
		QCompany qCompany = QCompany.company;
		Company company = queryFactory.selectFrom(qCompany).fetchAll().where(qCompany.id.eq(id)).fetchOne();
		
		return Optional.ofNullable(company);

		
	}
	
	public List<Company> list() {
		return queryFactory.selectFrom(QCompany.company).fetchAll().fetch();
	}
	
}
