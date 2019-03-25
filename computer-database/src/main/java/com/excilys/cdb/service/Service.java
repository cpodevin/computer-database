package com.excilys.cdb.service;

import com.excilys.cdb.dao.DAOFactory;

public class Service {

	public Service() { }
	
	public int getComputerNumber() {
		return DAOFactory.getInstance().getComputerDAO().list().size();
	}
	
	
}
