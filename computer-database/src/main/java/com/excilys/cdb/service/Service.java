package com.excilys.cdb.service;


import java.util.List;
import java.util.Optional;

import com.excilys.cdb.dao.DAOFactory;
import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;


public class Service {

	
	public Service() { }
	
	public Optional<Company> findCompany(int id) {
		return DAOFactory.getInstance().getCompanyDAO().find(id);
	}
	
	public List<Computer> getComputerList() {
		return DAOFactory.getInstance().getComputerDAO().list();
	}
	
	public List<Company> getCompanyList() {
		return DAOFactory.getInstance().getCompanyDAO().list();
	}
	
	public void createComputer(Computer computer) throws DAOException {
		DAOFactory.getInstance().getComputerDAO().create(computer);
	}
	
}
