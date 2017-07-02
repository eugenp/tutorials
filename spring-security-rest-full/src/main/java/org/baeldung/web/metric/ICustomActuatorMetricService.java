package org.baeldung.web.metric;

interface ICustomActuatorMetricService {

    void increaseCount(final int status);

    Object[][] getGraphData();
}
