package com.gergau.sensor.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartSeries;

import com.gergau.sensor.entities.Sensor;
import com.gergau.sensor.entities.SensorMeasure;

@Named
@SessionScoped
public class LightSensorModel implements Serializable {

	private Logger logger = Logger.getLogger(LightSensorModel.class.getName());

	private static final long serialVersionUID = 1L;

	private List<Sensor> sensors;

	private double minValue = 0;
	private double maxValue = 0;

	private CartesianChartModel linearModel = new CartesianChartModel();

	private void createLinearModel() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		linearModel.clear();
		for (Sensor sensor : sensors) {
			LineChartSeries lineChartSeries = new LineChartSeries();
			lineChartSeries.setLabel(sensor.getName() + "@"
					+ sensor.getLocation().getName());
			for (SensorMeasure measure : sensor.getLastMeasures()) {
				Double value = measure.getValue();
				lineChartSeries.set(sdf.format(measure.getMeasureTime()),
						value);
				if (value > getMaxValue()) {
					setMaxValue(value);
					logger.fine("Seeting new max value: " + getMaxValue());
				}
				if (value < getMinValue() && value < 0) {
					setMinValue(value);
					logger.fine("Seeting new min value: " + getMinValue());
				}
			}
			linearModel.addSeries(lineChartSeries);
		}
	}

	public CartesianChartModel getLinearModel() {
		createLinearModel();
		return linearModel;
	}

	public double getMaxValue() {
		return maxValue;
	}

	public double getMinValue() {
		return minValue;
	}

	public List<Sensor> getSensors() {
		return sensors;
	}

	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}

	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}
}