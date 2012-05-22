package com.buskify.entity;

import javax.persistence.Cacheable;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Cacheable
@AllArgsConstructor
@NoArgsConstructor
public class Settings {
	@Id Long id;
	private String name;
	private String value;
}