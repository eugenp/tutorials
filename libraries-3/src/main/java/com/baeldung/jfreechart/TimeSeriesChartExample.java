package com.baeldung.jfreechart;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class TimeSeriesChartExample {

    public static void main(String[] args) {
        // Create a dataset
        TimeSeries series = new TimeSeries("Monthly Sales");
        series.add(new Month(1, 2024), 200);
        series.add(new Month(2, 2024), 150);
        series.add(new Month(3, 2024), 180);

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series);

        // Create a chart using the dataset
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
            "Monthly Sales", // Chart title
            "Date",  // X-axis label
            "Sales", // Y-axis label
            dataset, // data
            true,    // legend
            false,   // tooltips
            false);  // no URLs

        // Display the chart
        ChartPanel chartPanel = new ChartPanel(chart);
        JFrame frame = new JFrame();
        frame.setSize(800, 600);
        frame.setContentPane(chartPanel);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}