package com.buskify.entity;

import lombok.Data;

import com.googlecode.objectify.Key;

@Data
public class Student extends AppUser implements java.io.Serializable {
	private static final long serialVersionUID = 1650476930164771007L;
	private Key<Project>[] projectPreferences;
	private Key<Project> assignedProject;
	private String number;
	private String course;
	private String stream;
	
	public Student(){}
	public Student(String username, String password) {
		super(username, password);
	}

}
