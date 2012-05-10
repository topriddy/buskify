package com.buskify.entity;

import javax.persistence.Id;

public class AppUser implements java.io.Serializable {
	@Id
	private Long id;
	private String username;
	private String password;
	
	public AppUser(){}
	
	
	public AppUser(String username, String password){
		this.username = username;
		this.password = password;
	}

	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}
	
}
