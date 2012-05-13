package com.buskify.entity;

import javax.persistence.Id;

import lombok.Data;

@Data
public class Settings {
	@Id Long id;
	private String name;
	private String value;
}