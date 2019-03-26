package com.excilys.cdb.dto;

import com.excilys.cdb.model.Computer;

public class ComputerDTO {

	private int id = 0;
	private String name = "";
	private String introduced = "";
	private String discontinued = "";
	private String company = "";
	private int companyId = 0;
	
	public ComputerDTO() { }
	
	public ComputerDTO(Computer computer) {
		this.id = computer.getId();
		this.name = computer.getName() != null ? computer.getName() : "";
		this.introduced = computer.getIntroduced() != null ? computer.getIntroduced().toString() : "";
		this.discontinued = computer.getDiscontinued() != null ? computer.getDiscontinued().toString() : "";
		this.company = computer.getCompany().isPresent() ? computer.getCompany().get().getName() : "";
		this.companyId = computer.getCompany().isPresent() ? computer.getCompany().get().getId() : 0 ;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getIntroduced() {
		return introduced;
	}
	
	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}
	
	public String getDiscontinued() {
		return discontinued;
	}
	
	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}
	
	public String getCompany() {
		return company;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}
	
	public int getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	
}
