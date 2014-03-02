package com.gergau.sensor.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@Entity
@NamedQueries({ @NamedQuery(name = Sensor.FIND_ALL, query = "select s from Sensor s") })
public class Sensor {
	public final static String FIND_ALL = "sensor.findAll";

	@Id
	@GeneratedValue
	private Long id;
	
	@Version
	private Long version;
	
	@NotNull
	private String name;

	@OneToMany(cascade=CascadeType.PERSIST)
	private List<SensorMeasure> measures;

	@NotNull
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Location location;

	public Location getLocation() {
		return location;
	}

	public List<SensorMeasure> getMeasures() {
		return measures;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setMeasures(List<SensorMeasure> measures) {
		this.measures = measures;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
