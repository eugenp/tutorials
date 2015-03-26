package org.baeldung.metric;

public interface IMetricService {

    void increaseCount(final int status);

    Object[][] getGraphData();
}
