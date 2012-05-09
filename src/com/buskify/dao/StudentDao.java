package com.buskify.dao;

import com.buskify.entity.Student;

public class StudentDao extends AbstractDao<Student>{

	public StudentDao() {
		super(Student.class);
	}
	
	public Student findByUsername(String username) {
		if (username == null || username.equals("")) {
			return null;
		}

		return ofy().query(Student.class).filter("username", username).get();
	}
}