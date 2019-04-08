package com.excilys.cdb.service;


import java.util.List;
import java.util.Optional;

import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.dao.DAOFactory;
import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.exception.InvalidInputException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;


public class ComputerService {
	
	public ComputerService() { }
	
	public Optional<Computer> find(int id) throws DAOException {
		return DAOFactory.getInstance().getComputerDAO().find(id);
	}
	public List<Computer> search(String search, int sort) throws DAOException {
		try {
			return DAOFactory.getInstance().getComputerDAO().search(search, ComputerDAO.Sort.values()[sort]);
		} catch (IndexOutOfBoundsException e) {
			return DAOFactory.getInstance().getComputerDAO().search(search, ComputerDAO.Sort.None);
		}
	}
	
	
	public List<Computer> getList() throws DAOException {
		return DAOFactory.getInstance().getComputerDAO().list();
	}
	
	public void create(Computer computer) throws DAOException, InvalidInputException {
		check(computer);
		DAOFactory.getInstance().getComputerDAO().create(computer);
	}
	
	public void update(Computer computer) throws DAOException, InvalidInputException {
		check(computer);
		DAOFactory.getInstance().getComputerDAO().update(computer);
	}
	
	public void delete(int id) throws DAOException {
		DAOFactory.getInstance().getComputerDAO().delete(new Computer(id,""));
	}
	
	
	private void check(Computer computer) throws InvalidInputException {
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
