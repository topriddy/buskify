package com.buskify.entity;

import javax.persistence.Id;

public class Student implements java.io.Serializable {
	private static final long serialVersionUID = 1650476930164771007L;
	
	@Id
	private Long id;
	private String username;
	private String password;

	public Student(){}
	public Student(String username, String password) {
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
