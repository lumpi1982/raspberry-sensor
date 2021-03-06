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

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.gergau.sensor.dao.SensorDao;
import com.gergau.sensor.entities.Sensor;
import com.gergau.sensor.entities.SensorMeasure;

@Stateless
public class SensorService {

	private Logger logger = Logger.getLogger(SensorService.class.getName());

	@Inject
	private SensorDao sensorDao;


	public void createSensor(Sensor sensor) {
		logger.log(Level.INFO, "Storing new Sensor ...");
		sensorDao.persist(sensor);
	}

	public void enhanceSensorWithLastMeasures(Sensor sensor) {
		List<SensorMeasure> lastMeasures = sensorDao.findLastMeasures(sensor,
				new Date(System.currentTimeMillis() - 24 * 3600 * 1000));
		sensor.setLastMeasures(lastMeasures);
	}

	public void enhanceSensorWithLastMeasures(Sensor sensor, int measureCount) {
		List<SensorMeasure> lastMeasures = sensorDao.findLastMeasures(sensor,measureCount);
		sensor.setLastMeasures(lastMeasures);
	}

	public List<Sensor> findSensors() {
		List<Sensor> sensors = sensorDao.findSensors();
		return sensors;
	}
}
