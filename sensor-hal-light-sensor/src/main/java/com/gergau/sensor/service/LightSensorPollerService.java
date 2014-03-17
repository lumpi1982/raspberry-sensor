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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

import com.gergau.sensor.dao.SensorDao;
import com.gergau.sensor.entities.Sensor;
import com.gergau.sensor.entities.SensorMeasure;

@Singleton
public class LightSensorPollerService {

	private Logger logger = Logger.getLogger(LightSensorPollerService.class
			.getName());

	@EJB
	private SensorDao sensorDao;

	private List<Sensor> lightSensors;

	public List<Sensor> getLightSensors() {
		return lightSensors;
	}

	public void setLightSensors(List<Sensor> lightSensors) {
		this.lightSensors = lightSensors;
	}

	@Schedule(second = "*/20", minute = "*", hour = "*")
	public synchronized void updateChart() {
		lightSensors = sensorDao.findLightSensors();
		for (Sensor lightSensor : lightSensors) {
			if (lightSensor.getName() == null) {
				lightSensor.setName("Sensor_" + lightSensor);
			}
			SensorMeasure measure = new SensorMeasure();
			measure.setMeasureTime(new Date());
			measure.setValue(Math.random() * 100);
			measure.setSensor(lightSensor);
			lightSensor.getMeasures().add(measure);
			logger.log(
					Level.INFO,
					"Setting Value " + measure.getValue() + " for Sensor "
							+ lightSensor.getName() + " at "
							+ measure.getMeasureTime() + " ...");
		}
	}
}
