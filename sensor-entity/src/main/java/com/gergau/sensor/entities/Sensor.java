package com.gergau.sensor.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@Entity
@NamedQueries({
	@NamedQuery(name = Sensor.FIND_ALL, query = "select s from Sensor s"),
	@NamedQuery(name = Sensor.FIND_BY_NAME, query = "select s from Sensor s where s.name=:name"),
		@NamedQuery(name = Sensor.DELETE_BY_NAME, query = "delete from Sensor s where s.name=:name") })
public class Sensor {
	public final static String FIND_ALL = "sensor.findAll";
	public final static String FIND_BY_NAME = "sensor.findByName";
	public final static String DELETE_BY_NAME = "sensor.deleteByName";

	@Id
	@GeneratedValue
	private Long id;

	@Version
	private Long version;

	@NotNull
	private String name;

	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private List<SensorMeasure> measures = new ArrayList<>();

	@Transient
	private List<SensorMeasure> lastMeasures = new ArrayList<>();

	@NotNull
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Location location;

	public List<SensorMeasure> getLastMeasures() {
		return lastMeasures;
	}

	public Location getLocation() {
		return location;
	}

	public List<SensorMeasure> getMeasures() {
		if (measures == null) {
			measures = new ArrayList<>();
		}
		return measures;
	}

	public String getName() {
		return name;
	}

	public void setLastMeasures(List<SensorMeasure> lastMeasures) {
		this.lastMeasures = lastMeasures;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setMeasures(List<SensorMeasure> measures) {
		this.measures = measures;
	}

	public void setName(String name) {
		this.name = name;
	}
}
