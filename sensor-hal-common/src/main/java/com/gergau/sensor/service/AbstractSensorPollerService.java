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

import java.util.logging.Logger;

import javax.inject.Inject;

import com.gergau.sensor.dao.SensorDao;
import com.gergau.sensor.entities.Location;
import com.gergau.sensor.entities.Sensor;


public abstract class AbstractSensorPollerService {

	private Logger logger = Logger.getLogger(AbstractSensorPollerService.class
			.getName());

	@Inject
	private SensorDao sensorDao;

	private Sensor sensor;

	protected Sensor findOrCreateSensor() {
		sensor = sensorDao.findLightSensorByName(getSensorName());
		if (sensor == null) {
			logger.info("Sensor for SensorName " + getSensorName()
					+ " is not available creating new one");
			sensor = new Sensor();
			sensor.setName(getSensorName());
			Location location = new Location();
			location.setName("Test Location");
			sensor.setLocation(location);
			sensorDao.persist(sensor);
		}
		return sensor;
	}

	protected abstract String getSensorName();

	public abstract void readFromSensor();
}
