package com.excilys.cdb.model;

public class Authority {
	
	private int id;
	private User username;
	private String authority;
	
	public Authority() {}
	
	public Authority(User username, String authority) {
		this.username = username;
		this.authority = authority;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUsername() {
		return username;
	}
	
	public void setUsername(User user) {
		this.username = user;
	}
	
	public String getAuthority() {
		return authority;
	}
	
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
}
