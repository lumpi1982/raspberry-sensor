package com.gergau.sensor.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@Entity
@NamedQueries({ @NamedQuery(name = SensorMeasure.LAST_MEASURES, query = "select sm from SensorMeasure sm where sm.sensor=:sensor order by sm.measureTime desc") })
public class SensorMeasure {

	public static final String LAST_MEASURES = "sensorMeasure.lastMeasures";

	@Id
	@GeneratedValue
	private Long id;

	@Version
	private Long version;

	@NotNull
	private Date measureTime;

	@NotNull
	private Double value;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch=FetchType.EAGER)
	@NotNull
	private Sensor sensor;

	public Date getMeasureTime() {
		return measureTime;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public Double getValue() {
		return value;
	}

	public void setMeasureTime(Date measureTime) {
		this.measureTime = measureTime;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	public void setValue(Double value) {
		this.value = value;
	}
}
