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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

import com.gergau.sensor.dao.SensorDao;
import com.gergau.sensor.entities.Location;
import com.gergau.sensor.entities.Sensor;
import com.gergau.sensor.entities.SensorMeasure;

@Singleton
public class LightSensorPollerService {

	private Logger logger = Logger.getLogger(LightSensorPollerService.class
			.getName());

	@Inject
	private SensorDao sensorDao;

	private static final String SENSOR_NAME = "Random Test Sensor";

	private Sensor sensor;

	@Schedule(second = "*/20", minute = "*", hour = "*")
	public synchronized void updateChart() {
		sensor = sensorDao.findLightSensorByName(SENSOR_NAME);
		if (sensor == null) {
			sensor = new Sensor();
			sensor.setName(SENSOR_NAME);
			Location location = new Location();
			location.setName("Test Location");
			sensor.setLocation(location);
		}
		SensorMeasure measure = new SensorMeasure();
		measure.setMeasureTime(new Date());
		measure.setValue(Math.random() * 100);
		measure.setSensor(sensor);
		sensor.getMeasures().add(measure);
		logger.log(
				Level.INFO,
				"Setting Value " + measure.getValue() + " for Sensor "
						+ sensor.getName() + " at "
						+ measure.getMeasureTime() + " ...");
		sensorDao.persist(sensor);
	}
}
