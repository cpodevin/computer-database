package dao;

import java.sql.Connection;

public abstract class DAO<T> {

	protected Connection conn = null;
	
	public DAO(Connection conn) {
		this.conn = conn;
	}
	
	public abstract boolean create(T object);
	
	public abstract boolean delete(T object);
	
	public abstract boolean update(T object);
	
	public abstract T find(int id);
	
}
