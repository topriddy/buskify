package com.buskify.entity;

import java.io.Serializable;

import javax.persistence.Id;

public class Admin implements Serializable {
	private static final long serialVersionUID = -2480894167521505743L;
	@Id
	private Long id;
	private String username;
	private String password;
	
	public Admin(){}

	public Admin(String username, String password) {
		this.username = username;
		this.password = password;
	}

	// getters and setters method below:
	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
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

}