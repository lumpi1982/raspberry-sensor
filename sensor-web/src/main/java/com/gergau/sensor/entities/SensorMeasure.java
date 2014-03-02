package com.gergau.sensor.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

@Entity
@NamedQueries({ @NamedQuery(name = SensorMeasure.LAST_MEASURES, query = "select sm from SensorMeasure sm order by sm.measureTime desc") })
public class SensorMeasure {

	public static final String LAST_MEASURES = "sensorMeasure.lastMeasures";

	@Id
	@GeneratedValue
	private Long id;

	private Date measureTime;

	private Double value;

	@ManyToOne(cascade = CascadeType.PERSIST)
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