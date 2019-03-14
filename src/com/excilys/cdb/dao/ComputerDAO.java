package com.excilys.cdb.dao;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerDAO extends DAO<Computer> {

	private final static ComputerDAO myInstance = new ComputerDAO(DAOConnection.getInstance());
	
	private final String createQuery = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
	private final String deleteQuery = "DELETE FROM computer WHERE id = ?";
	private final String updateQuery = "UPDATE computer SET name = ? , introduced = ? , discontinued = ? , company_id = ? WHERE id = ?";
	private final String findQuery = "SELECT * FROM computer WHERE id = ?";		
	private final String listQuery = "SELECT * FROM computer";
	
	private ComputerDAO(DAOConnection conn) {
		super(conn);
	}
	
	public static ComputerDAO getInstance() {
		return myInstance;
	}
	
	public boolean create(Computer computer) {		
		try (PreparedStatement statement = conn.getConn().prepareStatement(createQuery,Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, computer.getName());
			statement.setTimestamp(2, computer.getIntroduced());
			statement.setTimestamp(3, computer.getDiscontinued());
			if (computer.getCompany()==null) {
				statement.setNull(4, java.sql.Types.INTEGER);
			} else {
				statement.setInt(4, computer.getCompany().getId());
			}
			statement.executeUpdate();
			ResultSet result = statement.getGeneratedKeys();
			if (result.next()) {
				computer.setId(result.getInt(1));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(Computer computer) {	
		try (PreparedStatement statement = conn.getConn().prepareStatement(deleteQuery)) {		
			statement.setInt(1,  computer.getId());
			if (statement.executeUpdate()==1) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}		
	}

	public boolean update(Computer computer) {
		try (PreparedStatement statement = conn.getConn().prepareStatement(updateQuery)) {		
			statement.setString(1, computer.getName());
			statement.setTimestamp(2, computer.getIntroduced());
			statement.setTimestamp(3, computer.getDiscontinued());
			if (computer.getCompany()==null) {
				statement.setNull(4, java.sql.Types.INTEGER);
			} else {
				statement.setInt(4, computer.getCompany().getId());
			}
			statement.setInt(5, computer.getId());
			if (statement.executeUpdate()==1) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}		
	}
	
	public Computer find(int id) {
		try (PreparedStatement statement = conn.getConn().prepareStatement(findQuery)) {
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				CompanyDAO dao = DAOFactory.getInstance().getCompanyDAO();
				Company company = dao.find(result.getInt("company_id"));;
				return new Computer(result.getInt("id"), result.getString("name"), result.getTimestamp("introduced"), result.getTimestamp("discontinued"), company);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Computer> list() {
		List<Computer> resList = new ArrayList<>();
		
		try (PreparedStatement statement = conn.getConn().prepareStatement(listQuery)) {		
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				resList.add(new Computer(result.getInt("id"),result.getString("name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resList;
	}

	
}