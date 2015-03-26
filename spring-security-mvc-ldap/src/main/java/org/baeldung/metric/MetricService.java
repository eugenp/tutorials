package org.baeldung.metric;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.boot.actuate.metrics.repository.MetricRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MetricService implements IMetricService {

    @Autowired
    private MetricRepository repo;

    @Autowired
    private CounterService counter;

    private List<ArrayList<Integer>> statusMetric;
    private List<String> statusList;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public MetricService() {
        super();
        statusMetric = new ArrayList<ArrayList<Integer>>();
        statusList = new ArrayList<String>();
    }

    // API

    public void increaseCount(final int status) {
        counter.increment("status." + status);
        if (!statusList.contains("counter.status." + status)) {
            statusList.add("counter.status." + status);
        }
    }

    public Object[][] getGraphData() {
        Date current = new Date();
        int colCount = statusList.size() + 1;
        int rowCount = statusMetric.size() + 1;
        Object[][] result = new Object[rowCount][colCount];
        result[0][0] = "Time";

        int j = 1;
        for (final String status : statusList) {
            result[0][j] = status;
            j++;
        }

        List<Integer> temp;
        for (int i = 1; i < rowCount; i++) {
            temp = statusMetric.get(i - 1);
            result[i][0] = dateFormat.format(new Date(current.getTime() - (60000 * (rowCount - i))));
            for (j = 1; j <= temp.size(); j++) {
                result[i][j] = temp.get(j - 1);
            }
            while (j < colCount) {
                result[i][j] = 0;
                j++;
            }
        }
        return result;
    }

    // Non - API

    @Scheduled(fixedDelay = 60000)
    private void exportMetrics() {
        Metric<?> metric;
        ArrayList<Integer> statusCount = new ArrayList<Integer>();
        for (String status : statusList) {
            metric = repo.findOne(status);
            if (metric != null) {
                statusCount.add(metric.getValue().intValue());
                repo.reset(status);
            } else {
                statusCount.add(0);
            }

        }
        statusMetric.add(statusCount);
    }
}
