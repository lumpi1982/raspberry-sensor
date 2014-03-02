package com.gergau.sensor.model;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartSeries;

import com.gergau.sensor.entities.Sensor;

@Named
@ApplicationScoped
public class LightSensorModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Sensor sensor;

	private CartesianChartModel linearModel;

	public LightSensorModel() {
		createLinearModel();
	}

	private void createLinearModel() {
		linearModel = new CartesianChartModel();
		LineChartSeries series1 = new LineChartSeries();
		series1.setLabel("Series 1");
		series1.set(new Date(), Math.random());


		linearModel.addSeries(series1);
	}

	public CartesianChartModel getLinearModel() {
		return linearModel;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}
}