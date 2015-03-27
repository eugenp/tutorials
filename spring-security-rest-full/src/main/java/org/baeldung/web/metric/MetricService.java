package org.baeldung.web.metric;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class MetricService implements IMetricService {

    private Map<String, HashMap<Integer, Integer>> metricMap;
    private Map<Integer, Integer> statusMetric;
    private Map<String, HashMap<Integer, Integer>> timeMap;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public MetricService() {
        super();
        metricMap = new HashMap<String, HashMap<Integer, Integer>>();
        statusMetric = new HashMap<Integer, Integer>();
        timeMap = new HashMap<String, HashMap<Integer, Integer>>();
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
        Map<Integer, Integer> tempMap;
        for (final Entry<String, HashMap<Integer, Integer>> entry : timeMap.entrySet()) {
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
        HashMap<Integer, Integer> statusMap = metricMap.get(request);
        if (statusMap == null) {
            statusMap = new HashMap<Integer, Integer>();
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
        HashMap<Integer, Integer> statusMap = timeMap.get(time);
        if (statusMap == null) {
            statusMap = new HashMap<Integer, Integer>();
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
