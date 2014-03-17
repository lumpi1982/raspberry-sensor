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
package com.gergau.sensor.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Named;

import com.gergau.sensor.entities.Location;
import com.gergau.sensor.entities.Sensor;
import com.gergau.sensor.model.LightSensorModel;
import com.gergau.sensor.service.SensorService;

@Named
public class SensorController {

	Logger logger = Logger.getLogger(SensorController.class.getName());

	@Inject
	private LightSensorModel sensorModel;

	@Inject
	private SensorService sensorService;

	public void createSensor() {
		logger.log(Level.INFO, "Storing new Sensor ...");
		Sensor sensor = new Sensor();
		sensor.setLocation(new Location());
		sensor.setName("TestSensor_" + sensor);
		sensorService.createSensor(sensor);
		sensorModel.getSensors().add(sensor);
	}

	public String initModel() {
		logger.log(Level.INFO, "Init Model ...");
		List<Sensor> sensors = sensorService.findSensors();
		for (Sensor sensor : sensors) {
			logger.fine("Found sensor with name: " + sensor.getName());
			sensorService.enhanceSensorWithLastMeasures(sensor, 20);
		}
		logger.log(Level.FINER, "Found " + sensors.size() + " sensors.");
		sensorModel.setSensors(sensors);
		return "";
	}

}
