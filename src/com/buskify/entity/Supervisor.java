package com.buskify.entity;


public class Supervisor extends AppUser implements java.io.Serializable {
	private static final long serialVersionUID = 5520573745493203230L;

	public Supervisor(){}
	public Supervisor(String username, String password) {
		super(username, password);
	}
}