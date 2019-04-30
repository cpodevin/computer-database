package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.dao.DAOFactory;
import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.model.Company;

public class CompanyService {
	
	public CompanyService() { }
	
	private CompanyDAO companyDAO;
	
	public Optional<Company> find(int id) {
		return companyDAO.find(id);
	}
	
	public List<Company> getList() {
		return companyDAO.list();
	}
	
	public void delete(Company company) throws DAOException {
		companyDAO.delete(company);
	}

	public CompanyDAO getCompanyDAO() {
		return companyDAO;
	}

	public void setCompanyDAO(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}
	
}
