package com.baeldung.metrics.service;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemoryMetricService implements MetricService {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private final Map<String, Map<Integer, Integer>> metricMap;
    private final Map<Integer, Integer> statusMetric;
    private final Map<String, Map<Integer, Integer>> timeMap;

    public InMemoryMetricService() {
        metricMap = new ConcurrentHashMap<>();
        statusMetric = new ConcurrentHashMap<>();
        timeMap = new ConcurrentHashMap<>();
    }

    public void increaseCount(String request, int status) {
        increaseMainMetric(request, status);
        increaseStatusMetric(status);
        updateTimeMap(status);
    }

    public Map<String, Map<Integer, Integer>> getFullMetric() {
        return metricMap;
    }

    public Map<Integer, Integer> getStatusMetric() {
        return statusMetric;
    }

    public Object[][] getGraphData() {
        final int colCount = statusMetric.keySet().size() + 1;
        final Set<Integer> allStatus = statusMetric.keySet();
        final int rowCount = timeMap.keySet().size() + 1;

        final Object[][] result = new Object[rowCount][colCount];
        result[0][0] = "Time";

        int j = 1;
        for (final int status : allStatus) {
            result[0][j] = status;
            j++;
        }
        int i = 1;
        Map<Integer, Integer> tempMap;
        for (final Entry<String, Map<Integer, Integer>> entry : timeMap.entrySet()) {
            result[i][0] = entry.getKey();
            tempMap = entry.getValue();
            for (j = 1; j < colCount; j++) {
                result[i][j] = tempMap.get((Integer) result[0][j]);
                if (result[i][j] == null) {
                    result[i][j] = 0;
                }
            }
            i++;
        }

        for (int k = 1; k < result[0].length; k++) {
            result[0][k] = result[0][k].toString();
        }

        return result;
    }

    private void increaseMainMetric(String request, int status) {
        Map<Integer, Integer> statusMap = metricMap.get(request);
        if (statusMap == null) {
            statusMap = new ConcurrentHashMap<>();
        }

        Integer count = statusMap.get(status);
        if (count == null) {
            count = 1;
        } else {
            count++;
        }
        statusMap.put(status, count);
        metricMap.put(request, statusMap);
    }

    private void increaseStatusMetric(int status) {
        statusMetric.merge(status, 1, Integer::sum);
    }

    private void updateTimeMap(int status) {
        final String time = DATE_FORMAT.format(new Date());
        Map<Integer, Integer> statusMap = timeMap.get(time);
        if (statusMap == null) {
            statusMap = new ConcurrentHashMap<>();
        }

        Integer count = statusMap.get(status);
        if (count == null) {
            count = 1;
        } else {
            count++;
        }
        statusMap.put(status, count);
        timeMap.put(time, statusMap);
    }

}
