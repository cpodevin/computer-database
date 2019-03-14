package com.excilys.cdb.dao;

public class DAOFactory {

	private final static DAOFactory myInstance = new DAOFactory();
	
	private DAOFactory() { }
	
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
