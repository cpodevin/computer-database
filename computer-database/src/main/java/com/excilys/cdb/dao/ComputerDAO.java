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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerDAO {

	private static final Logger logger = LoggerFactory.getLogger(com.excilys.cdb.controller.Main.class);
	
	private static ComputerDAO myInstance = null;
	private DAOFactory factory;
	
	private final String createQuery = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
	private final String deleteQuery = "DELETE FROM computer WHERE id = ?";
	private final String updateQuery = "UPDATE computer SET name = ? , introduced = ? , discontinued = ? , company_id = ? WHERE id = ?";
	private final String findQuery = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE id = ?";		
	private final String listQuery = "SELECT id,name,introduced,discontinued,company_id FROM computer";
	
	private ComputerDAO(DAOFactory conn) {
		this.factory = conn;
	}
	
	public static ComputerDAO getInstance(DAOFactory conn) {
		if (myInstance == null) {
			myInstance = new ComputerDAO(conn);		
		}
		return myInstance;
	}
	
	public boolean create(Computer computer) {		
		try (Connection conn = factory.getConn(); 
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
				return true;
			}
		} catch (SQLException e) {
			logger.error("SQL : ", e);
		}
		return false;
	}
	
	public boolean delete(Computer computer) {	
		try (Connection conn = factory.getConn(); 
				PreparedStatement statement = conn.prepareStatement(deleteQuery)) {		
			statement.setInt(1,  computer.getId());
			if (statement.executeUpdate()==1) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			logger.error("SQL : ", e);
			return false;
		}		
	}

	public boolean update(Computer computer) {
		try (Connection conn = factory.getConn(); 
				PreparedStatement statement = conn.prepareStatement(updateQuery)) {		
			statement.setString(1, computer.getName());
			statement.setDate(2, computer.getIntroduced());
			statement.setDate(3, computer.getDiscontinued());
			if (!computer.getCompany().isPresent()) {
				statement.setNull(4, java.sql.Types.INTEGER);
			} else {
				statement.setInt(4, computer.getCompany().get().getId());
			}
			statement.setInt(5, computer.getId());
			if (statement.executeUpdate()==1) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			logger.error("SQL : ", e);
			return false;
		}		
	}
	
	public Optional<Computer> find(int id) {
		try (Connection conn = factory.getConn(); 
				PreparedStatement statement = conn.prepareStatement(findQuery)) {
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				Optional<Company> company = DAOFactory.getInstance().getCompanyDAO().find(result.getInt("company_id"));;
				return Optional.of(new Computer(result.getInt("id"), result.getString("name"), result.getDate("introduced"), result.getDate("discontinued"), company));
			}
		} catch (SQLException e) {
			logger.error("SQL : ", e);
		}
		return Optional.empty();
	}
	
	public List<Computer> list() {

		List<Company> companyList = DAOFactory.getInstance().getCompanyDAO().list();
		Map<Integer,Company> companyMap  = new HashMap<>();
		for (Company company : companyList) {
			companyMap.put(company.getId(),company);
		}
		
		List<Computer> resList = new ArrayList<>();
		
		try (Connection conn = factory.getConn(); 
				PreparedStatement statement = conn.prepareStatement(listQuery)) {		
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Optional<Company> company = companyMap.containsKey(result.getInt("company_id")) ? Optional.of(companyMap.get(result.getInt("company_id"))) : Optional.empty();
				resList.add(new Computer(result.getInt("id"), result.getString("name"), result.getDate("introduced"), result.getDate("discontinued"), company));
			}
		} catch (SQLException e) {
			logger.error("SQL : ", e);
		}
		return resList;
	}

	
}