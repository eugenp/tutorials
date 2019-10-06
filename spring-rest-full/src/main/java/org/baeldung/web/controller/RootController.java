package org.baeldung.web.controller;

import java.util.Map;

import org.baeldung.web.metric.IActuatorMetricService;
import org.baeldung.web.metric.IMetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/auth/")
public class RootController {

    @Autowired
    private IMetricService metricService;

    @Autowired
    private IActuatorMetricService actMetricService;

    public RootController() {
        super();
    }

    // API

    @RequestMapping(value = "/metric", method = RequestMethod.GET)
    @ResponseBody
    public Map getMetric() {
        return metricService.getFullMetric();
    }

    @RequestMapping(value = "/status-metric", method = RequestMethod.GET)
    @ResponseBody
    public Map getStatusMetric() {
        return metricService.getStatusMetric();
    }

    @RequestMapping(value = "/metric-graph", method = RequestMethod.GET)
    @ResponseBody
    public Object[][] drawMetric() {
        final Object[][] result = metricService.getGraphData();
        for (int i = 1; i < result[0].length; i++) {
            result[0][i] = result[0][i].toString();
        }
        return result;
    }


}
