/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.gergau.sensor.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.primefaces.model.chart.ChartSeries;

import com.gergau.sensor.entities.Location;
import com.gergau.sensor.entities.Sensor;
import com.gergau.sensor.entities.SensorMeasure;
import com.gergau.sensor.model.LightSensorModel;

@Stateless
public class SensorService {

	@PersistenceContext(unitName = "sensor-pu")
	private EntityManager entityManager;

	@Inject
	private LightSensorModel bean;

	private List<Sensor> lightSensors;

	public void createSensor() {
		System.out.println("Storing new Sensor ...");
		Sensor sensor = new Sensor();
		sensor.setLocation(new Location());
		entityManager.persist(sensor);
		System.out.println("... Stored new Sensor");
	}

	public List<SensorMeasure> fetchLastMeasures() {
		TypedQuery<SensorMeasure> query = entityManager.createNamedQuery(
				SensorMeasure.LAST_MEASURES, SensorMeasure.class)
				.setMaxResults(40);

		return query.getResultList();
	}

	private List<Sensor> findLightSensors() {
		return entityManager.createNamedQuery(Sensor.FIND_ALL, Sensor.class)
				.getResultList();
	}

	public List<Sensor> getLightSensors() {
		return lightSensors;
	}

	public void setLightSensors(List<Sensor> lightSensors) {
		this.lightSensors = lightSensors;
	}

	public void storeMeasure(SensorMeasure measure) {
		entityManager.persist(measure);
	}

	@Schedule(second = "*/2", minute = "*", hour = "*")
	public void updateChart() {
		if (lightSensors == null || lightSensors.size() == 0) {
			lightSensors = findLightSensors();
		}
		for (Sensor lightSensor : lightSensors) {
			System.out.println("Setting more Values ...");
			ChartSeries chartSeries = bean.getLinearModel().getSeries().get(0);
			SensorMeasure measure = new SensorMeasure();
			measure.setMeasureTime(new Date());
			measure.setValue(Math.random() * 100);
			List<SensorMeasure> measures = fetchLastMeasures();
			measure.setSensor(lightSensor);
			chartSeries.getData().clear();
			for(SensorMeasure measure2 : measures) {
				chartSeries.getData().put(measure2.getMeasureTime(),
						measure2.getValue());
			}
			storeMeasure(measure);
		}
	}
}
