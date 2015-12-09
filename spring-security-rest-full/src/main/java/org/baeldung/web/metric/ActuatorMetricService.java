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
public class ActuatorMetricService implements IActuatorMetricService {

    @Autowired
    private MetricReaderPublicMetrics publicMetrics;

    private final List<ArrayList<Integer>> statusMetric;
    private final List<String> statusList;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public ActuatorMetricService() {
        super();
        statusMetric = new ArrayList<ArrayList<Integer>>();
        statusList = new ArrayList<String>();
    }


    @Override
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
        final ArrayList<Integer> statusCount = initializeCounterList(statusList.size());

        for (final Metric<?> counterMetric : publicMetrics.metrics()) {
            updateStatusCount(counterMetric, statusCount);
        }

        statusMetric.add(statusCount);
    }

    private ArrayList<Integer> initializeCounterList(final int size) {
        final ArrayList<Integer> counterList = new ArrayList<Integer>();
        for (int i = 0; i < size; i++) {
            counterList.add(0);
        }
        return counterList;
    }

    private void updateStatusCount(final Metric<?> counterMetric, final ArrayList<Integer> statusCount) {
        String status = "";
        int index = -1;
        int oldCount = 0;

        if (counterMetric.getName().contains("counter.status.")) {
            status = counterMetric.getName().substring(15, 18); // example 404, 200
            if (!statusList.contains(status)) {
                statusList.add(status);
                statusCount.add(0);
            }
            index = statusList.indexOf(status);
            oldCount = statusCount.get(index) == null ? 0 : statusCount.get(index);
            statusCount.set(index, counterMetric.getValue().intValue() + oldCount);
        }
    }

    //
}