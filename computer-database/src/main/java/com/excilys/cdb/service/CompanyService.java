package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;

import com.excilys.cdb.dao.DAOFactory;
import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.model.Company;

public class CompanyService {
	
	public CompanyService() { }
	
	public Optional<Company> find(int id) {
		return DAOFactory.getInstance().getCompanyDAO().find(id);
	}
	
	public List<Company> getList() {
		return DAOFactory.getInstance().getCompanyDAO().list();
	}
	
	public void delete(Company company) throws DAOException {
		DAOFactory.getInstance().getCompanyDAO().delete(company);
	}
	
}
