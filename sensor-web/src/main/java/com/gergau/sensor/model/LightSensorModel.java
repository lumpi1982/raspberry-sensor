package com.gergau.sensor.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartSeries;

import com.gergau.sensor.entities.Sensor;

@Named
@RequestScoped
public class LightSensorModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Sensor> sensors;

	private CartesianChartModel linearModel =new CartesianChartModel();


	private void createLinearModel() {
		linearModel.clear();
		for (Sensor sensor : sensors) {
			LineChartSeries lineChartSeries = new LineChartSeries();
			lineChartSeries.setLabel(sensor.getName());
			linearModel.addSeries(lineChartSeries);
		}
	}

	public CartesianChartModel getLinearModel() {
		createLinearModel();
		return linearModel;
	}

	public List<Sensor> getSensors() {
		return sensors;
	}

	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}
}