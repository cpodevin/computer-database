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

import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerDAO {
	
	private static ComputerDAO myInstance = null;
	private DAOFactory factory;
	
	private final String createQuery = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
	private final String deleteQuery = "DELETE FROM computer WHERE id = ?";
	private final String updateQuery = "UPDATE computer SET name = ? , introduced = ? , discontinued = ? , company_id = ? WHERE id = ?";
	private final String findQuery = "SELECT c.id,c.name,c.introduced,c.discontinued,c.company_id,d.name FROM computer c LEFT JOIN company d ON c.company_id=d.id WHERE c.id = ?";		
	private final String listQuery = "SELECT c.id,c.name,c.introduced,c.discontinued,c.company_id,d.name FROM computer c LEFT JOIN company d ON c.company_id=d.id";
	private final String searchQuery = "SELECT c.id,c.name,c.introduced,c.discontinued,c.company_id,d.name FROM computer c LEFT JOIN company d ON c.company_id=d.id WHERE c.name LIKE ? OR d.name LIKE ?";
	
	private ComputerDAO(DAOFactory conn) {
		this.factory = conn;
	}
	
	public enum Sort {
		None, NameAsc, NameDesc, IntroducedAsc, IntroducedDesc, DiscontinuedAsc, DiscontinuedDesc, CompanyAsc, CompanyDesc
	};
	
	public static ComputerDAO getInstance(DAOFactory conn) {
		if (myInstance == null) {
			myInstance = new ComputerDAO(conn);		
		}
		return myInstance;
	}
	
	public void create(Computer computer) throws DAOException {	
		try (Connection conn = DataSource.getConn(); 
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
			conn.commit();
		} catch (SQLException e) {
			throw new DAOException(e);
		}

	}
	
	public void delete(Computer computer) throws DAOException {
		try (Connection conn = DataSource.getConn(); 
				PreparedStatement statement = conn.prepareStatement(deleteQuery)) {		
			statement.setInt(1,  computer.getId());
			if (statement.executeUpdate()!=1) {
				throw new DAOException("No line found to delete.");
			}
			conn.commit();
		} catch (SQLException e) {
			throw new DAOException(e);
		}		
	}

	public void update(Computer computer) throws DAOException {
		try (Connection conn = DataSource.getConn(); 
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
			if (statement.executeUpdate()!=1) {
				throw new DAOException("No line found to update.");
			}
			conn.commit();
		} catch (SQLException e) {
			throw new DAOException(e);
		}		
	}
	
	public Optional<Computer> find(int id) throws DAOException {
		try (Connection conn = DataSource.getConn(); 
				PreparedStatement statement = conn.prepareStatement(findQuery)) {
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				return Optional.of(new Computer(result.getInt("c.id"), result.getString("c.name"), result.getDate("c.introduced"), result.getDate("c.discontinued"), result.getInt("c.company_id")!=0 ? Optional.of(new Company(result.getInt("c.company_id"),result.getString("d.name"))) : Optional.empty()));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return Optional.empty();
	}
	
	public List<Computer> list() throws DAOException {
		
		List<Computer> resList = new ArrayList<>();
		
		try (Connection conn = DataSource.getConn(); 
				PreparedStatement statement = conn.prepareStatement(listQuery)) {		
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				resList.add(new Computer(result.getInt("c.id"), result.getString("c.name"), result.getDate("c.introduced"), result.getDate("c.discontinued"), result.getInt("c.company_id")!=0 ? Optional.of(new Company(result.getInt("c.company_id"),result.getString("d.name"))) : Optional.empty()));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return resList;
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
		
		try (Connection conn = DataSource.getConn(); 
				PreparedStatement statement = conn.prepareStatement(query)) {
			statement.setString(1, "%" + search + "%");
			statement.setString(2, "%" + search + "%");
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				resList.add(new Computer(result.getInt("c.id"), result.getString("c.name"), result.getDate("c.introduced"), result.getDate("c.discontinued"), result.getInt("c.company_id")!=0 ? Optional.of(new Company(result.getInt("c.company_id"),result.getString("d.name"))) : Optional.empty()));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return resList;
	}

	
}