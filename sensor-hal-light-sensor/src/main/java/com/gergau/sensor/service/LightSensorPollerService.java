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

import com.gergau.sensor.entities.Sensor;
import com.gergau.sensor.entities.SensorMeasure;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.RaspiPin;

@Singleton
public class LightSensorPollerService extends AbstractSensorPollerService {

	private Logger logger = Logger.getLogger(LightSensorPollerService.class
			.getName());
	private static final long WAIT_TIMER = 1;

	@Override
	protected String getSensorName() {
		return "Light Sensor";
	}

	@Override
	@Schedule(second = "*/20", minute = "*", hour = "*")
	public synchronized void readFromSensor() {
		Sensor sensor = findOrCreateSensor();
		SensorMeasure measure = new SensorMeasure();
		measure.setMeasureTime(new Date());
		measure.setValue((double) readSensor());
		measure.setSensor(sensor);
		sensor.getMeasures().add(measure);
		logger.info(
				"Setting Value " + measure.getValue() + " for Sensor "
						+ sensor.getName() + " at "
						+ measure.getMeasureTime() + " ...");
	}

	private long readSensor() {
		GpioController gpio = GpioFactory.getInstance();
		GpioPinDigitalOutput sensorPin = gpio.provisionDigitalMultipurposePin(
				RaspiPin.GPIO_01, PinMode.DIGITAL_OUTPUT);
		while (sensorPin.isHigh()) {
			try {
				Thread.sleep(WAIT_TIMER);
			} catch (InterruptedException e) {
				logger.log(Level.SEVERE, "Got an exception: " + e.getMessage(),
						e);
			}
			logger.info("Waiting till Pin is pulled to low ...");
		}
		long count = 0;
		sensorPin.setMode(PinMode.DIGITAL_INPUT);
		while (sensorPin.isLow()) {
			count++;
		}
		logger.fine("Changed after: " + count);
		gpio.unprovisionPin(sensorPin);
		return count;
	}

}
