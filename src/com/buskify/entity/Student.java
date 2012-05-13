package com.buskify.entity;

import com.googlecode.objectify.Key;


public class Student extends AppUser implements java.io.Serializable {
	private static final long serialVersionUID = 1650476930164771007L;
	private Key<Project>[] projectPreferences;
	private Key<Project> assignedProject;
	
	public Student(){}
	public Student(String username, String password) {
		super(username, password);
	}

}
