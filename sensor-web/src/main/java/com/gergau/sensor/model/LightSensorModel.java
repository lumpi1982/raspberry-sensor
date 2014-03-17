package com.gergau.sensor.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartSeries;

import com.gergau.sensor.entities.Sensor;
import com.gergau.sensor.entities.SensorMeasure;

@Named
@SessionScoped
public class LightSensorModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Sensor> sensors;

	private CartesianChartModel linearModel = new CartesianChartModel();

	private void createLinearModel() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		linearModel.clear();
		for (Sensor sensor : sensors) {
			LineChartSeries lineChartSeries = new LineChartSeries();
			lineChartSeries.setLabel(sensor.getName());
			for (SensorMeasure measure : sensor.getLastMeasures()) {
				lineChartSeries.set(sdf.format(measure.getMeasureTime()),
						measure.getValue());
			}
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