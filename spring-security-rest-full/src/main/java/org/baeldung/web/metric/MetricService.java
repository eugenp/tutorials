package org.baeldung.web.metric;

import java.util.HashMap;

import org.springframework.stereotype.Service;

@Service
public class MetricService {

    private static HashMap<String, HashMap<Integer, Integer>> metricMap = new HashMap<String, HashMap<Integer, Integer>>();

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
    }

    public String getFullMetric() {
        return metricMap.entrySet().toString();
    }
}
