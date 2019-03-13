package dao;

import java.sql.Connection;

public class DAOFactory {

	private final static DAOFactory myInstance = new DAOFactory();
	private Connection conn;
	private ComputerDAO myComputerDAO;
	private CompanyDAO myCompanyDAO;
	
	private DAOFactory() {
		conn = DAOConnection.getConn();
	}
	
	public static DAOFactory getInstance() {
		return myInstance;
	}
	
	public ComputerDAO getComputerDAO() {
		return ComputerDAO.getInstance();
	}
	
	public CompanyDAO getCompanyDAO() {
		return CompanyDAO.getInstance();
	}
	
}
