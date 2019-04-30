package com.excilys.cdb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "company")
public class Company {

	@Id
	@Column(name = "id")
	@GeneratedValue
	private int id = 0;
	
	@Column(name = "name")
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
