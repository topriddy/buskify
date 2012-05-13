package com.buskify.entity;

import lombok.Data;

import com.googlecode.objectify.Key;

@Data
public class Supervisor extends AppUser implements java.io.Serializable {
	private static final long serialVersionUID = 5520573745493203230L;
	private Key<Project>[] projects;
	
	public Supervisor(){}
	public Supervisor(String username, String password) {
		super(username, password);
	}
}