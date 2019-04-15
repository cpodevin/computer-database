package com.excilys.cdb.dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.excilys.cdb.dto.ComputerMapper;
import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerDAO {
	
	private final String createQuery = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
	private final String deleteQuery = "DELETE FROM computer WHERE id = ?";
	private final String updateQuery = "UPDATE computer SET name = ? , introduced = ? , discontinued = ? , company_id = ? WHERE id = ?";
	private final String findQuery = "SELECT c.id,c.name,c.introduced,c.discontinued,c.company_id,d.name FROM computer c LEFT JOIN company d ON c.company_id=d.id WHERE c.id = ?";		
	private final String listQuery = "SELECT c.id,c.name,c.introduced,c.discontinued,c.company_id,d.name FROM computer c LEFT JOIN company d ON c.company_id=d.id";
	private final String searchQuery = "SELECT c.id,c.name,c.introduced,c.discontinued,c.company_id,d.name FROM computer c LEFT JOIN company d ON c.company_id=d.id WHERE c.name LIKE ? OR d.name LIKE ?";
	
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

	public enum Sort {
		None, NameAsc, NameDesc, IntroducedAsc, IntroducedDesc, DiscontinuedAsc, DiscontinuedDesc, CompanyAsc, CompanyDesc
	};
	
	public void create(Computer computer) throws DAOException {	
		try (Connection conn = dataSource.getConnection(); 
				PreparedStatement statement = conn.prepareStatement(createQuery,Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, computer.getName());
			statement.setDate(2, computer.getIntroduced());
			statement.setDate(3, computer.getDiscontinued());
			if (!computer.getCompany().isPresent()) {
				statement.setNull(4, java.sql.Types.INTEGER);
			} else {
				statement.setInt(4, computer.getCompany().get().getId());
			}
			statement.executeUpdate();
			ResultSet result = statement.getGeneratedKeys();
			if (result.next()) {
				computer.setId(result.getInt(1));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}

	}
	
	public void delete(Computer computer) throws DAOException {
		try (Connection conn = dataSource.getConnection(); 
				PreparedStatement statement = conn.prepareStatement(deleteQuery)) {		
			statement.setInt(1,  computer.getId());
			if (statement.executeUpdate()!=1) {
				throw new DAOException("No line found to delete.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}		
	}

	public void update(Computer computer) throws DAOException {	
		int nbRowAffected = jdbcTemplate.update(updateQuery, computer.getName(), computer.getIntroduced(), computer.getDiscontinued(), computer.getCompany().isPresent() ? computer.getCompany().get().getId() : null, computer.getId());
		
		if (nbRowAffected != 1) {
			throw new DAOException("No line found to update.");
		}
	}
	
	public Optional<Computer> find(int id) throws DAOException {
		return Optional.ofNullable(jdbcTemplate.queryForObject(findQuery, computerMapper, id));
	}
	
	public List<Computer> list() throws DAOException {
		return jdbcTemplate.query(listQuery, new ComputerMapper());
	}
	
	public List<Computer> search(String search, Sort sort) throws DAOException {
		
		List<Computer> resList = new ArrayList<>();
		
		String query = searchQuery;
		
		switch (sort) {
		case NameAsc :
			query += " ORDER BY CASE WHEN c.name IS null THEN 1 ELSE 0 END, c.name";
			break;
		case NameDesc :
			query += " ORDER BY c.name DESC";
			break;
		case IntroducedAsc :
			query += " ORDER BY CASE WHEN c.introduced IS null THEN 1 ELSE 0 END, c.introduced";
			break;
		case IntroducedDesc :
			query += " ORDER BY c.introduced DESC";
			break;
		case DiscontinuedAsc :
			query += " ORDER BY CASE WHEN c.discontinued IS null THEN 1 ELSE 0 END, c.discontinued";
			break;
		case DiscontinuedDesc :
			query += " ORDER BY c.discontinued DESC";
			break;	
		case CompanyAsc :
			query += " ORDER BY CASE WHEN d.name IS null THEN 1 ELSE 0 END, d.name";
			break;
		case CompanyDesc :
			query += " ORDER BY d.name DESC";
			break;
		default :
		}
		
		return jdbcTemplate.query(query,new String[] {"%" + search + "%", "%" + search + "%"}, computerMapper);
	}


	
}