package dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Company;
import model.Computer;

public class ComputerDAO extends DAO<Computer> {

	private final static ComputerDAO myInstance = new ComputerDAO(DAOConnection.getConn());
	
	private ComputerDAO(Connection conn) {
		super(conn);
	}
	
	public static ComputerDAO getInstance() {
		return myInstance;
	}
	
	public boolean create(Computer computer) {
		String query = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
		ResultSet result;
		
		try {
			PreparedStatement statement = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, computer.getName());
			statement.setTimestamp(2, computer.getIntroduced());
			statement.setTimestamp(3, computer.getDiscontinued());
			if (computer.getCompany()==null) {
				statement.setNull(4, java.sql.Types.INTEGER);
			} else {
				statement.setInt(4, computer.getCompany().getId());
			}
			statement.executeUpdate();
			result = statement.getGeneratedKeys();
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
		String query = "DELETE FROM computer WHERE id = ?";
		
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1,  computer.getId());
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}		
	}

	public boolean update(Computer computer) {
		String query = "UPDATE computer SET name = ? , introduced = ? , discontinued = ? , company_id = ? WHERE id = ?";
		
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, computer.getName());
			statement.setTimestamp(2, computer.getIntroduced());
			statement.setTimestamp(3, computer.getDiscontinued());
			if (computer.getCompany()==null) {
				statement.setNull(4, java.sql.Types.INTEGER);
			} else {
				statement.setInt(4, computer.getCompany().getId());
			}
			statement.setInt(5, computer.getId());
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}		
	}
	
	public Computer find(int id) {
		String query = "SELECT * FROM computer WHERE id = ?";
		ResultSet result;
		
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, id);
			result = statement.executeQuery();
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
		String query = "SELECT * FROM computer";
		ResultSet result;
		List<Computer> resList = new ArrayList<>();
		
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			result = statement.executeQuery();
			while (result.next()) {
				resList.add(new Computer(result.getInt("id"),result.getString("name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resList;
	}

	
}