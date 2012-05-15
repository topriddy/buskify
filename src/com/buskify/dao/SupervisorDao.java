package com.buskify.dao;

import java.util.List;

import com.buskify.entity.Supervisor;

public class SupervisorDao extends AbstractDao<Supervisor> {
	public SupervisorDao(){
		super(Supervisor.class);
	}
	
	public void deleteAllExemptDefaultSupervisor(){
		List<Supervisor> supervisors = findAll();
		Supervisor defaultSupervisor = findByUsername("supervisor");
		if(defaultSupervisor != null && supervisors.contains(defaultSupervisor)){
			supervisors.remove(defaultSupervisor);
		}
		ofy().delete(supervisors);
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