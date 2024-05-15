package com.baeldung.metrics.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CustomActuatorMetricService implements MetricService {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Autowired
    private MeterRegistry registry;

    private final List<List<Integer>> statusMetricsByMinute;
    private final List<String> statusList;

    public CustomActuatorMetricService() {
        statusMetricsByMinute = new ArrayList<>();
        statusList = new ArrayList<>();
    }

    public void increaseCount(int status) {
        String counterName = "counter.status." + status;
        registry.counter(counterName).increment();
        if (!statusList.contains(counterName)) {
            statusList.add(counterName);
        }
    }

    @Override
    public Object[][] getGraphData() {
        final Date current = new Date();
        final int colCount = statusList.size() + 1;
        final int rowCount = statusMetricsByMinute.size() + 1;
        final Object[][] result = new Object[rowCount][colCount];
        result[0][0] = "Time";

        int j = 1;
        for (final String status : statusList) {
            result[0][j] = status;
            j++;
        }

        for (int i = 1; i < rowCount; i++) {
            result[i][0] = DATE_FORMAT.format(new Date(current.getTime() - (60000L * (rowCount - i))));
        }

        List<Integer> minuteOfStatuses;
        for (int i = 1; i < rowCount; i++) {
            minuteOfStatuses = statusMetricsByMinute.get(i - 1);
            for (j = 1; j <= minuteOfStatuses.size(); j++) {
                result[i][j] = minuteOfStatuses.get(j - 1);
            }
            while (j < colCount) {
                result[i][j] = 0;
                j++;
            }
        }
        return result;
    }

    @Scheduled(fixedDelayString = "${fixedDelay.in.milliseconds:60000}")
    private void exportMetrics() {
        List<Integer> statusCount = new ArrayList<>();
        for (final String status : statusList) {
            Search search = registry.find(status);
            Counter counter = search.counter();
            if (counter == null) {
                statusCount.add(0);
            } else {
                statusCount.add((int) counter.count());
                registry.remove(counter);
            }
        }
        statusMetricsByMinute.add(statusCount);
    }
}