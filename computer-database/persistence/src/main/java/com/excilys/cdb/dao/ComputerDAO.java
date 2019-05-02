package com.excilys.cdb.dao;

import java.sql.Statement;
import java.io.File;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.support.TransactionTemplate;

import com.excilys.cdb.dto.ComputerMapper;
import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.model.Computer;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.codegen.JPADomainExporter;
import com.querydsl.jpa.hibernate.HibernateQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.excilys.cdb.model.*;

public class ComputerDAO {
	
	private final String createQuery = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
	private final String deleteQuery = "DELETE FROM computer WHERE id = ?";
	private final String updateQuery = "UPDATE computer SET name = ? , introduced = ? , discontinued = ? , company_id = ? WHERE id = ?";
	private final String findQuery = "SELECT c.id,c.name,c.introduced,c.discontinued,c.company_id,d.name FROM computer c LEFT JOIN company d ON c.company_id=d.id WHERE c.id = ?";		
	private final String listQuery = "SELECT c.id,c.name,c.introduced,c.discontinued,c.company_id,d.name FROM computer c LEFT JOIN company d ON c.company_id=d.id";
	private final String searchQuery = "SELECT c.id,c.name,c.introduced,c.discontinued,c.company_id,d.name FROM computer c LEFT JOIN company d ON c.company_id=d.id WHERE c.name LIKE ? OR d.name LIKE ?";
	
	private static final Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
	
	public ComputerDAO() { }

	private DriverManagerDataSource dataSource;
	
	public DriverManagerDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DriverManagerDataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	private ComputerMapper computerMapper;

	public ComputerMapper getComputerMapper() {
		return computerMapper;
	}

	public void setComputerMapper(ComputerMapper computerMapper) {
		this.computerMapper = computerMapper;
	}
	
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private JPAQueryFactory queryFactory;

	public JPAQueryFactory getQueryFactory() {
		return queryFactory;
	}

	public void setQueryFactory(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}
	
	private DataSourceTransactionManager transactionManager;
	
	public DataSourceTransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(DataSourceTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}


	public enum Sort {
		None, NameAsc, NameDesc, IntroducedAsc, IntroducedDesc, DiscontinuedAsc, DiscontinuedDesc, CompanyAsc, CompanyDesc
	};
	
	public void create(Computer computer) throws DAOException {	

//		KeyHolder keyHolder = new GeneratedKeyHolder();
//		int nbRowAffected = jdbcTemplate.update(
//				c -> { PreparedStatement statement = c.prepareStatement(createQuery,Statement.RETURN_GENERATED_KEYS);
//				statement.setString(1, computer.getName());
//				statement.setDate(2, computer.getIntroduced());
//				statement.setDate(3, computer.getDiscontinued());
//				if (computer.getCompany() == null) {
//					statement.setNull(4, java.sql.Types.INTEGER);
//				} else {
//					statement.setInt(4, computer.getCompany().getId());
//				}
//				return statement;
//		}, keyHolder);
//		if (nbRowAffected != 1) {
//			logger.warn("DB error : No line created.");
//			throw new DAOException("No line created.");
//		}
//		computer.setId(((BigInteger) keyHolder.getKey()).intValue());
		
		Session session = sessionFactory.openSession();
		session.save(computer);
		session.close();
		
	}
	
	public void delete(Computer computer) throws DAOException {
//		int nbRowAffected = jdbcTemplate.update(deleteQuery, computer.getId());
		
		QComputer qComputer = QComputer.computer;
		queryFactory.delete(qComputer).where(qComputer.id.eq(computer.getId())).execute();

		
//		if (nbRowAffected != 1) {
//			logger.warn("DB error : No line found to delete.");
//			throw new DAOException("No line found to delete.");
//		}
	}

	public void update(Computer computer) throws DAOException {	
//		int nbRowAffected = jdbcTemplate.update(updateQuery, computer.getName(), 
//				computer.getIntroduced(), computer.getDiscontinued(), 
//				computer.getCompany(),
//				computer.getId());
//		
//		if (nbRowAffected != 1) {
//			logger.warn("DB error : No line found to update.");
//			throw new DAOException("No line found to update.");
//		}
		
//		QComputer qComputer = QComputer.computer;
//		if (queryFactory.update(qComputer).where(qComputer.id.eq(computer.getId())).set(qComputer.name, computer.getName())
//		.set(qComputer.introduced, computer.getIntroduced()).set(qComputer.discontinued,  computer.getDiscontinued()).execute() != 1) {
//			logger.warn("DB error : No line found to update.");
//			throw new DAOException("No line found to update.");
//		}		
		
		Session session = sessionFactory.openSession();
		session.save(computer);
		session.close();
		
	}
	
	public Optional<Computer> find(int id) {
	
		QComputer qComputer = QComputer.computer;
		Computer computer = queryFactory.selectFrom(qComputer).fetchAll().where(qComputer.id.eq(id)).fetchOne();
		
		return Optional.ofNullable(computer);

	}
	
	public List<Computer> list() throws DAOException {
		
		QComputer qComputer = QComputer.computer;
		List<Computer> computers = queryFactory.selectFrom(qComputer).fetchAll().fetch();
		
		return computers;
	}
	
	public List<Computer> search(String search, Sort sort) throws DAOException {
		
		QComputer qComputer = QComputer.computer;
		List<Computer> computers = queryFactory.selectFrom(qComputer).fetchAll().where(qComputer.name.like("%" + search + "%")).orderBy(getOrder(sort)).fetch();
		
		return computers;
	}


	private OrderSpecifier<?> getOrder(Sort sort) {
		switch (sort) {
		case NameAsc :
			return QComputer.computer.name.asc();
		case NameDesc :
			return QComputer.computer.name.desc();
		case IntroducedAsc :
			return QComputer.computer.introduced.asc().nullsLast();
		case IntroducedDesc :
			return QComputer.computer.introduced.desc().nullsLast();
		case DiscontinuedAsc :
			return QComputer.computer.discontinued.asc().nullsLast();
		case DiscontinuedDesc :
			return QComputer.computer.discontinued.desc().nullsLast();
		case CompanyAsc :
			return QComputer.computer.company.name.asc().nullsLast();
		case CompanyDesc :
			return QComputer.computer.company.name.desc().nullsLast();
		default :
			return QComputer.computer.id.asc();
		}
	}
	
}