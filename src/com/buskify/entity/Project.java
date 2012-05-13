package com.buskify.entity;

import javax.persistence.Id;

import lombok.Data;

import com.googlecode.objectify.Key;
import java.io.Serializable;

@Data
public class Project implements Serializable {
	@Id
	private Long id;
	private String title;
	private int max = 1;
	private String description;
	private Key<Supervisor>supervisor;
	
	public Project(){
		
	}
	public Project(String title, int max){
		this.title = title;
		this.max = max;
	}
}