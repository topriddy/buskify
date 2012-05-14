package com.buskify.dao;

import java.util.List;

import com.buskify.entity.Project;
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
	
	public List<Student> findByProject(Project project){
		return ofy().query(Student.class).filter("assignedProject", project).list();
	}
	
}