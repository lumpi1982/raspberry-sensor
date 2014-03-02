package com.gergau.sensor.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Location {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@OneToMany
	private List<Sensor> sensors;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
