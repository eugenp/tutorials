package com.baeldung.commons.math3;

import org.apache.commons.math3.stat.Frequency;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler;

import java.util.*;

public class Histogram {

    public Histogram() {

        Map distributionMap = processRawData();
        List yData = new ArrayList();
        yData.addAll(distributionMap.values());
        List xData = Arrays.asList(distributionMap.keySet().toArray());

        CategoryChart chart = buildChart(xData, yData);
        new SwingWrapper<>(chart).displayChart();

    }

    private CategoryChart buildChart(List xData, List yData) {

        // Create Chart
        CategoryChart chart = new CategoryChartBuilder().width(800).height(600)
                .title("Age Distribution")
                .xAxisTitle("Age Group")
                .yAxisTitle("Frequency")
                .build();

        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setAvailableSpaceFill(0.99);
        chart.getStyler().setOverlapped(true);

        chart.addSeries("age group", xData, yData);

        return chart;
    }

    private Map processRawData() {

        List<Integer> datasetList = Arrays.asList(36, 25, 38, 46, 55, 68, 72, 55, 36, 38, 67, 45, 22, 48, 91, 46, 52, 61, 58, 55);
        Frequency frequency = new Frequency();
        datasetList.forEach(d -> frequency.addValue(Double.parseDouble(d.toString())));

        int classWidth = 10;

        Map distributionMap = new TreeMap();
        List processed = new ArrayList();
        datasetList.forEach(d -> {

            double observation = Double.parseDouble(d.toString());

            if(processed.contains(observation))
                return;

            long observationFrequency = frequency.getCount(observation);
            int upperBoundary = (observation > classWidth) ? Math.multiplyExact( (int) Math.ceil(observation / classWidth), classWidth) : classWidth;
            int lowerBoundary = (upperBoundary > classWidth) ? Math.subtractExact(upperBoundary, classWidth) : 0;
            String bin = lowerBoundary + "-" + upperBoundary;

            int prevUpperBoundary = lowerBoundary;
            int prevLowerBoundary = (lowerBoundary > classWidth) ? lowerBoundary - classWidth : 0;
            String prevBin = prevLowerBoundary + "-" + prevUpperBoundary;
            if(!distributionMap.containsKey(prevBin))
                distributionMap.put(prevBin, 0);

            if(!distributionMap.containsKey(bin)) {
                distributionMap.put(bin, observationFrequency);
            }
            else {
                long oldFrequency = Long.parseLong(distributionMap.get(bin).toString());
                distributionMap.replace(bin, oldFrequency + observationFrequency);
            }

            processed.add(observation);

        });

        return distributionMap;
    }

    public static void main(String[] args) {
        new Histogram();
    }

}
