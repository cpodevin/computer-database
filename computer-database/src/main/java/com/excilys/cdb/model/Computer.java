package com.excilys.cdb.model;

import java.sql.Date;

public class Computer {

	private int id = 0;
	private String name = "";
	private Date introduced = null;
	private Date discontinued = null;
	private Company company = null;
	
	public Computer(int id, String name, Date introduced, Date discontinued, Company company) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}
	
	public Computer(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Computer() {}
	
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
	
	public Date getIntroduced() {
		return introduced;
	}
	
	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}
	
	public Date getDiscontinued() {
		return discontinued;
	}
	
	public void setDiscontinued(Date discontinued) {
		this.discontinued = discontinued;
	}
	
	public Company getCompany() {
		return company;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
@Override
	public boolean equals(Object obj) {
		if (this==obj) return true;
		if (obj==null) return false;
		if (obj instanceof Computer) {
			return (id == ((Computer) obj).getId());
		}
		return false;
	}
}
