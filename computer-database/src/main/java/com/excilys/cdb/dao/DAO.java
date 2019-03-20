package com.excilys.cdb.dao;

import java.util.List;
import java.util.Optional;

import com.excilys.cdb.model.Company;

public abstract class DAO<T> {

	protected DAOConnection conn = null;
	
	public DAO(DAOConnection conn) {
		this.conn = conn;
	}
	
	public abstract boolean create(T object);
	
	public abstract boolean delete(T object);
	
	public abstract boolean update(T object);
	
	public abstract Optional<T> find(int id);
	
	public abstract List<T> list();
	
}
