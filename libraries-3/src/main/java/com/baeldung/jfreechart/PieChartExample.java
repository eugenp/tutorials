package com.baeldung.jfreechart;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class PieChartExample {

    public static void main(String[] args) {
        // Create a dataset
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        dataset.setValue("January", 200);
        dataset.setValue("February", 150);
        dataset.setValue("March", 180);

        // Create a chart using the dataset
        JFreeChart chart = ChartFactory.createPieChart(
            "Monthly Sales", // Chart title
            dataset, // data
            true,    // include legend
            true,    // generate tool tips
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