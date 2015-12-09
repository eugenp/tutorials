package org.baeldung.web.metric;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.MetricReaderPublicMetrics;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ActuatorMetricService2 {

    @Autowired
    private MetricReaderPublicMetrics publicMetrics;

    private final List<ArrayList<Integer>> statusMetric;
    private final List<String> statusList;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public ActuatorMetricService2() {
        super();
        statusMetric = new ArrayList<ArrayList<Integer>>();
        statusList = new ArrayList<String>();
    }


    public Object[][] getGraphData() {
        final Date current = new Date();
        final int colCount = statusList.size() + 1;
        final int rowCount = statusMetric.size() + 1;
        final Object[][] result = new Object[rowCount][colCount];
        result[0][0] = "Time";
        int j = 1;
        for (final String status : statusList) {
            result[0][j] = status;
            j++;
        }

        List<Integer> temp;
        List<Integer> last = new ArrayList<Integer>();
        for (int i = 1; i < rowCount; i++) {
            temp = statusMetric.get(i - 1);
            result[i][0] = dateFormat.format(new Date(current.getTime() - (60000 * (rowCount - i))));
            for (j = 1; j <= temp.size(); j++) {
                System.out.println(last);
                result[i][j] = temp.get(j - 1) - (last.size() >= j ? last.get(j - 1) : 0);
            }
            while (j < colCount) {
                result[i][j] = 0;
                j++;
            }
            last = temp;
        }
        return result;
    }

    // Non - API

    @Scheduled(fixedDelay = 60000)
    private void exportMetrics() {
        final ArrayList<Integer> statusCount = new ArrayList<Integer>();
        String status = "";
        int index = -1;
        int old = 0;
        for (int i = 0; i < statusList.size(); i++) {
            statusCount.add(0);
        }
        for (final Metric<?> metric : publicMetrics.metrics()) {
            if (metric.getName().contains("counter.status.")) {
                status = metric.getName().substring(15, 18);
                if (!statusList.contains(status)) {
                    statusList.add(status);
                    statusCount.add(0);
                }
                System.out.println(statusList + " == " + statusCount);
                index = statusList.indexOf(status);
                old = statusCount.get(index) == null ? 0 : statusCount.get(index);
                statusCount.set(index, metric.getValue().intValue() + old);
                // metric.set(0);
                // repo.reset(metric.getName());
            }
        }
        statusMetric.add(statusCount);

        // for (final Metric<?> metric : publicMetrics.metrics()) {
        // System.out.println(metric.getName() + " = " + metric.getValue() + " = " + metric.getTimestamp());
        // }
    }
}