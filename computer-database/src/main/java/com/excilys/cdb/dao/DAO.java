package com.excilys.cdb.dao;

public abstract class DAO<T> {

	protected DAOConnection conn = null;
	
	public DAO(DAOConnection conn) {
		this.conn = conn;
	}
	
	public abstract boolean create(T object);
	
	public abstract boolean delete(T object);
	
	public abstract boolean update(T object);
	
	public abstract T find(int id);
	
}
