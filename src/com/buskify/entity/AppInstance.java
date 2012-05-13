package com.buskify.entity;

import java.util.Date;

import javax.persistence.Id;

import lombok.Data;
@Data
public class AppInstance {
	@Id Long id;
	private String name;
	private Date startDate;
	private Date endDate;
	private String description;
}
