package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Company;

public class CompanyDAO extends DAO<Company> {

	private final static CompanyDAO myInstance = new CompanyDAO(DAOConnection.getConn());
	
	private CompanyDAO(Connection conn) {
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
		String query = "SELECT * FROM company WHERE id = ?";
		ResultSet result;
		
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, id);
			result = statement.executeQuery();
			if (result.next()) {
				return new Company(result.getInt("id"),result.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Company> list() {
		String query = "SELECT * FROM company";
		ResultSet result;
		List<Company> resList = new ArrayList<>();
		
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			result = statement.executeQuery();
			while (result.next()) {
				resList.add(new Company(result.getInt("id"),result.getString("name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resList;
	}
	
}
