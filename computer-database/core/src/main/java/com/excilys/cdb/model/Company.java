package com.excilys.cdb.model;


public class Company {

	private int id = 0;
	private String name = "";
	
	public Company(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Company() {}
	
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
	
@Override
	public boolean equals(Object obj) {
		if (this==obj) return true;
		if (obj==null) return false;
		if (obj instanceof Company) {
			return (id == ((Company) obj).getId());
		}
		return false;
	}
}
