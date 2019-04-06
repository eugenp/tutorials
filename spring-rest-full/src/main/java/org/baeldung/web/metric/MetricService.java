package org.baeldung.web.metric;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Service;

@Service
public class MetricService implements IMetricService {

    private ConcurrentMap<String, ConcurrentHashMap<Integer, Integer>> metricMap;
    private ConcurrentMap<Integer, Integer> statusMetric;
    private ConcurrentMap<String, ConcurrentHashMap<Integer, Integer>> timeMap;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public MetricService() {
        super();
        metricMap = new ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>>();
        statusMetric = new ConcurrentHashMap<Integer, Integer>();
        timeMap = new ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>>();
    }

    // API

    @Override
    public void increaseCount(final String request, final int status) {
        increaseMainMetric(request, status);
        increaseStatusMetric(status);
        updateTimeMap(status);
    }

    @Override
    public Map getFullMetric() {
        return metricMap;
    }

    @Override
    public Map getStatusMetric() {
        return statusMetric;
    }

    @Override
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
        ConcurrentMap<Integer, Integer> tempMap;
        for (final Entry<String, ConcurrentHashMap<Integer, Integer>> entry : timeMap.entrySet()) {
            result[i][0] = entry.getKey();
            tempMap = entry.getValue();
            for (j = 1; j < colCount; j++) {
                result[i][j] = tempMap.get(result[0][j]);
                if (result[i][j] == null) {
                    result[i][j] = 0;
                }
            }
            i++;
        }

        return result;
    }

    // NON-API

    private void increaseMainMetric(final String request, final int status) {
        ConcurrentHashMap<Integer, Integer> statusMap = metricMap.get(request);
        if (statusMap == null) {
            statusMap = new ConcurrentHashMap<Integer, Integer>();
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

    private void increaseStatusMetric(final int status) {
        final Integer statusCount = statusMetric.get(status);
        if (statusCount == null) {
            statusMetric.put(status, 1);
        } else {
            statusMetric.put(status, statusCount + 1);
        }
    }

    private void updateTimeMap(final int status) {
        final String time = dateFormat.format(new Date());
        ConcurrentHashMap<Integer, Integer> statusMap = timeMap.get(time);
        if (statusMap == null) {
            statusMap = new ConcurrentHashMap<Integer, Integer>();
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
