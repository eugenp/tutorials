package com.baeldung.metrics.controller;

import com.baeldung.metrics.service.InMemoryMetricService;
import com.baeldung.metrics.service.MetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping(value = "/metrics")
@ResponseBody
public class MetricsController {

    @Autowired
    private InMemoryMetricService metricService;

    // change the qualifier to use the in-memory implementation
    @Autowired
    @Qualifier("customActuatorMetricService")
    private MetricService graphMetricService;

    @GetMapping(value = "/metric")
    public Map<String, Map<Integer, Integer>> getMetric() {
        return metricService.getFullMetric();
    }

    @GetMapping(value = "/status-metric")
    public Map<Integer, Integer> getStatusMetric() {
        return metricService.getStatusMetric();
    }

    @GetMapping(value = "/metric-graph-data")
    public Object[][] getMetricData() {
        return graphMetricService.getGraphData();
    }
}
