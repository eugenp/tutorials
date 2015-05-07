package org.baeldung.web.metric;

public interface IActuatorMetricService {

    void increaseCount(final int status);

    Object[][] getGraphData();
}
