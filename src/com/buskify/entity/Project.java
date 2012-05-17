package com.buskify.entity;

import javax.persistence.Cacheable;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

import com.googlecode.objectify.Key;
import java.io.Serializable;

@Data
@Cacheable
public class Project implements Serializable {
	@Id
	private Long id;
	private String title;
	private int max = 1;
	private String description;
	private Key<Supervisor>supervisor;
	
	//some transient attribs useful for allocation algorithm
	@Transient int assignedCount;
	
	public Project(){
		
	}
	public Project(String title, int max){
		this.title = title;
		this.max = max;
	}
}