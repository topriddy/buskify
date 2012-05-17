package com.buskify.entity;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Id;

@Cacheable
public class Admin extends AppUser implements Serializable {
	private static final long serialVersionUID = -2480894167521505743L;

	public Admin(){}
	public Admin(String username, String password) {
		super(username, password);
	}
}