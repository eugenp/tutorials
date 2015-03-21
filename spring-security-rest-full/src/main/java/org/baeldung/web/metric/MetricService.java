package org.baeldung.web.metric;

import java.util.HashMap;

import org.springframework.stereotype.Service;

@Service
public class MetricService {

    private static HashMap<String, HashMap<Integer, Integer>> metricMap = new HashMap<String, HashMap<Integer, Integer>>();
    private static HashMap<Integer, Integer> statusMetric = new HashMap<Integer, Integer>();

    public MetricService() {
        super();
    }

    public void increaseCount(final String request, final int status) {
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

        final Integer statusCount = statusMetric.get(status);
        if (statusCount == null) {
            statusMetric.put(status, 1);
        } else {
            statusMetric.put(status, statusCount + 1);
        }
    }

    public String getFullMetric() {
        return metricMap.entrySet().toString();
    }

    public String getStatusMetric() {
        return statusMetric.entrySet().toString();
    }
}
