package com.buskify.entity;

import javax.persistence.Id;

import lombok.Data;

import com.googlecode.objectify.Key;

@Data
public class Project {
	@Id
	private Long id;
	private String title;
	private int max = 1;
	private String description;
	
	public Project(){
		
	}
	public Project(String title, int max){
		this.title = title;
		this.max = max;
	}
}