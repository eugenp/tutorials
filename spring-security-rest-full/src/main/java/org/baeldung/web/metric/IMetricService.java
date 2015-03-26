package org.baeldung.web.metric;

public interface IMetricService {

    void increaseCount(final String request, final int status);

    String getFullMetric();

    String getStatusMetric();

    Object[][] getGraphData();
}
