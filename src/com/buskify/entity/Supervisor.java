package com.buskify.entity;

import javax.persistence.Cacheable;
import javax.persistence.Transient;

import lombok.Data;

import com.googlecode.objectify.Key;

@Data
@Cacheable
public class Supervisor extends AppUser implements java.io.Serializable {
	private static final long serialVersionUID = 5520573745493203230L;
	@Transient private  int assignedCount;
	public Supervisor(){}
	public Supervisor(String username, String password) {
		super(username, password);
	}
}