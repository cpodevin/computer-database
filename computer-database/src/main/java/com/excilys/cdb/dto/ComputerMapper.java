package com.excilys.cdb.dto;

import java.sql.Date;
import java.util.Optional;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;

public class ComputerMapper {
		
	private CompanyService companyService;

	public ComputerMapper() { }
	
	public Computer getComputer(ComputerDTO dto) {
		
		Computer res = new Computer();
		
		res.setId(dto.getId());
		res.setName(dto.getName());
		try {
			res.setIntroduced(Date.valueOf(dto.getIntroduced()));
		} catch (IllegalArgumentException e) {
			res.setIntroduced(null);
		}
		try {
			res.setDiscontinued(Date.valueOf(dto.getDiscontinued()));
		} catch (IllegalArgumentException e) {
			res.setDiscontinued(null);
		}
		res.setCompany(dto.getCompanyId()!=0 ? Optional.of(new Company(dto.getCompanyId(),dto.getCompany())) : Optional.empty());
		
		return res;
	}
	
	public CompanyService getService() {
		return companyService;
	}

	public void setService(CompanyService companyService) {
		this.companyService = companyService;
	}

}
