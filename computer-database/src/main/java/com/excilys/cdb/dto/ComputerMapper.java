package com.excilys.cdb.dto;

import java.sql.Date;
import java.text.SimpleDateFormat;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.Service;

public class ComputerMapper {
		
	private Service service;
	
	public ComputerMapper() {
		service = new Service();
	}
	
	public Computer getComputer(ComputerDTO dto) {
		
		Computer res = new Computer();
		
		res.setId(dto.getId());
		res.setName(dto.getName());
		res.setIntroduced(Date.valueOf(dto.getIntroduced()));
		res.setDiscontinued(Date.valueOf(dto.getDiscontinued()));
		res.setCompany(service.findCompany(dto.getCompanyId()));
		
		return res;
	}
	
}
