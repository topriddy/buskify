package com.buskify.entity;


public class Student extends AppUser implements java.io.Serializable {
	private static final long serialVersionUID = 1650476930164771007L;

	public Student(){}
	public Student(String username, String password) {
		super(username, password);
	}

}
