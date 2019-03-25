package com.excilys.cdb.service;


import java.util.List;

import com.excilys.cdb.dao.DAOFactory;
import com.excilys.cdb.model.Computer;


public class Service {

	
	public Service() { }
	
	public List<Computer> getComputerList() {
		return DAOFactory.getInstance().getComputerDAO().list();
	}
	
	
}
