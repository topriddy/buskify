package com.buskify.entity;

import javax.persistence.Id;

import lombok.Data;

@Data
public class AppUser implements java.io.Serializable {
	@Id
	private Long id;
	private String username;
	private String password;
	private String fullName;
	
	public AppUser(){}
	
	
	public AppUser(String username, String password){
		this.username = username;
		this.password = password;
	}
}
