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
package com.gergau.sensor.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.gergau.sensor.entities.Sensor;
import com.gergau.sensor.entities.SensorMeasure;

@Stateless
public class SensorDao {

	@PersistenceContext(unitName = "sensor-pu")
	private EntityManager entityManager;

	public List<SensorMeasure> fetchLastMeasures() {
		TypedQuery<SensorMeasure> query = entityManager.createNamedQuery(
				SensorMeasure.LAST_MEASURES, SensorMeasure.class)
				.setMaxResults(40);

		return query.getResultList();
	}

	public Sensor findLightSensorByName(String name) {
		TypedQuery<Sensor> query = entityManager.createNamedQuery(
				Sensor.FIND_BY_NAME, Sensor.class);
		query.setParameter("name", name);
		List<Sensor> resultList = query.getResultList();
		if (resultList == null || resultList.size() != 1) {
			return null;
		}
		// Sensor sensor = query.getSingleResult();
		// return sensor;
		return resultList.get(0);
	}

	public List<Sensor> findSensors() {
		List<Sensor> resultList = entityManager.createNamedQuery(Sensor.FIND_ALL, Sensor.class)
				.getResultList();
		if(resultList==null) {
			resultList=new ArrayList<>();
		}
		return resultList;
	}

	public void persist(Sensor sensor) {
		entityManager.persist(sensor);
	}
}
