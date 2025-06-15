package com.baeldung.jfreechart;

import javax.swing.*;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class CombinationChartExample {

    public static void main(String[] args) {
        // Create datasets
        DefaultCategoryDataset lineDataset = new DefaultCategoryDataset();
        lineDataset.addValue(200, "Sales", "January");
        lineDataset.addValue(150, "Sales", "February");
        lineDataset.addValue(180, "Sales", "March");

        DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
        barDataset.addValue(400, "Profit", "January");
        barDataset.addValue(300, "Profit", "February");
        barDataset.addValue(250, "Profit", "March");

        // Create a combination chart
        CategoryPlot plot = new CategoryPlot();
        plot.setDataset(0, lineDataset);
        plot.setRenderer(0, new LineAndShapeRenderer());

        plot.setDataset(1, barDataset);
        plot.setRenderer(1, new BarRenderer());

        plot.setDomainAxis(new CategoryAxis("Month"));
        plot.setRangeAxis(new NumberAxis("Value"));

        plot.setOrientation(PlotOrientation.VERTICAL);
        plot.setRangeGridlinesVisible(true);
        plot.setDomainGridlinesVisible(true);

        JFreeChart chart = new JFreeChart(
            "Monthly Sales and Profit", // chart title
            null,  // null means to use default font
            plot,  // combination chart as CategoryPlot
            true); // legend

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