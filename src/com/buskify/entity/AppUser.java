package com.buskify.entity;

public class AppUser implements java.io.Serializable {
	private String username;
	private String password;
	
	public AppUser(String username, String password){
		this.username = username;
		this.password = password;
	}
}