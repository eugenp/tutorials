package com.baeldung.paimon;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetricReader {

    Logger logger = LoggerFactory.getLogger(MetricReader.class);

    List<Metric> readMetrics() {
        //read the metrics from the file
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("metrics.out");

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        String CREATED_BY = "admin";
        List<Metric> metrics = new ArrayList<>();
        boolean isFirstLine = true;
        try {
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String deviceId = parts[0];
                    String metricsName = parts[1];

  
                   String createTime = parts[2];
                    
                    long metricsValue = (long) Double.parseDouble(parts[3]);
                    String source = parts[4];
                    logger.info("Read metric: deviceId={}, metricsName={}, createTime={}, metricsValue={}, source={}",
                            deviceId, metricsName, createTime, metricsValue, source);

                    Metric metric = new Metric(deviceId, metricsName, metricsValue, source, createTime, CREATED_BY);
                    //add the metric to the list
                    metrics.add(metric);
                }
            }
        } catch (Exception e) {
            logger.error("Error reading metrics", e);
        }

        return metrics;
    }   
}
