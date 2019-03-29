package com.excilys.cdb.service;


import java.util.List;
import java.util.Optional;

import com.excilys.cdb.dao.DAOFactory;
import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.exception.InvalidInputException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;


public class Service {

	
	public Service() { }
	
	public Optional<Company> findCompany(int id) {
		return DAOFactory.getInstance().getCompanyDAO().find(id);
	}
	
	public List<Company> getCompanyList() {
		return DAOFactory.getInstance().getCompanyDAO().list();
	}
	
	public Optional<Computer> findComputer(int id) throws DAOException {
		return DAOFactory.getInstance().getComputerDAO().find(id);
	}
	
	public List<Computer> getComputerList() throws DAOException {
		return DAOFactory.getInstance().getComputerDAO().list();
	}
	
	public void createComputer(Computer computer) throws DAOException, InvalidInputException {
		checkComputer(computer);
		DAOFactory.getInstance().getComputerDAO().create(computer);
	}
	
	public void updateComputer(Computer computer) throws DAOException, InvalidInputException {
		checkComputer(computer);
		DAOFactory.getInstance().getComputerDAO().update(computer);
	}
	
	public void deleteComputer(int id) throws DAOException {
		DAOFactory.getInstance().getComputerDAO().delete(new Computer(id,""));
	}
	
	
	private void checkComputer(Computer computer) throws InvalidInputException {
		if ("".equals(computer.getName().trim())) {
			throw new InvalidInputException("Computer name can't be empty.");
		}
		if (computer.getIntroduced() == null && computer.getDiscontinued() != null) {
			throw new InvalidInputException("Computer have an discontinuation date but lack an introduction date.");
		}
		if (computer.getIntroduced() != null && computer.getDiscontinued() != null && computer.getIntroduced().after(computer.getDiscontinued())) {
			throw new InvalidInputException("Introduction date must be before discontinuation date.");
		}
	}
	
}
