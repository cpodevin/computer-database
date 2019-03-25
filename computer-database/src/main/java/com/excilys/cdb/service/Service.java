package com.excilys.cdb.service;


import com.excilys.cdb.dao.DAOFactory;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

public class Service {

	
	public Service() { }
	
	public Page<Computer> getComputerList() {
		return new Page<Computer>(DAOFactory.getInstance().getComputerDAO().list());
	}
	
	
}
