package com.baeldung.metrics.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ActuatorMetricService implements MetricService {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Autowired
    private MeterRegistry publicMetrics;

    private final List<List<Integer>> statusMetricsByMinute;
    private final List<String> statusList;

    public ActuatorMetricService() {
        statusMetricsByMinute = new ArrayList<>();
        statusList = new ArrayList<>();
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
        List<Integer> last = new ArrayList<>();

        for (int i = 1; i < rowCount; i++) {
            minuteOfStatuses = statusMetricsByMinute.get(i - 1);
            for (j = 1; j <= minuteOfStatuses.size(); j++) {
                result[i][j] = minuteOfStatuses.get(j - 1) - (last.size() >= j ? last.get(j - 1) : 0);
            }
            while (j < colCount) {
                result[i][j] = 0;
                j++;
            }
            last = minuteOfStatuses;
        }
        return result;
    }

    @Scheduled(fixedDelayString = "${fixedDelay.in.milliseconds:60000}")
    private void exportMetrics() {
        final List<Integer> lastMinuteStatuses = initializeStatuses(statusList.size());

        for (final Meter counterMetric : publicMetrics.getMeters()) {
            updateMetrics(counterMetric, lastMinuteStatuses);
        }

        statusMetricsByMinute.add(lastMinuteStatuses);
    }

    private List<Integer> initializeStatuses(int size) {
        List<Integer> counterList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            counterList.add(0);
        }
        return counterList;
    }

    private void updateMetrics(Meter counterMetric, List<Integer> statusCount) {

        String metricName = counterMetric.getId().getName();

        if (metricName.contains("counter.status.")) {
            // example 404, 200
            String status = metricName.substring(15, 18);
            appendStatusIfNotExist(status, statusCount);
            int index = statusList.indexOf(status);
            int oldCount = statusCount.get(index) == null ? 0 : statusCount.get(index);
            statusCount.set(index, (int)((Counter) counterMetric).count() + oldCount);
        }
    }

    private void appendStatusIfNotExist(String status, List<Integer> statusCount) {
        if (!statusList.contains(status)) {
            statusList.add(status);
            statusCount.add(0);
        }
    }
}
