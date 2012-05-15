package com.buskify.dao;

import com.buskify.entity.Supervisor;

public class SupervisorDao extends AbstractDao<Supervisor> {
	public SupervisorDao(){
		super(Supervisor.class);
	}
	
	public Supervisor findByUsername(String username) {
		if (username == null || username.equals("")) {
			return null;
		}

		return ofy().query(Supervisor.class).filter("username", username).get();
	}
	
	public Supervisor findByFullName(String fullName) {
		if (fullName == null || fullName.equals("")) {
			return null;
		}

		return ofy().query(Supervisor.class).filter("fullName", fullName.trim()).get();
	}
	
	
}