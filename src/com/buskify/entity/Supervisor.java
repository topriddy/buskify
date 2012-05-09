package com.buskify.entity;

import javax.persistence.Id;

public class Supervisor implements java.io.Serializable{
	private static final long serialVersionUID = 5520573745493203230L;
	
	@Id
	private Long id;
	private String username;
	private String password;
	
	public Supervisor(){}
	public Supervisor(String username, String password){
		this.username = username;
		this.password = password;
	}


	//getters and setters method below:
	
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