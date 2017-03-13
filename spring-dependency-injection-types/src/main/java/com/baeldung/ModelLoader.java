package com.baeldung;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModelLoader {
    private XmlModelParser xmlModelParser;
    private MetricsSender metricsSender;
    private static Logger log = LoggerFactory.getLogger(ModelLoader.class);

    public ModelLoader(XmlModelParser xmlModelParser) {
        this.xmlModelParser = xmlModelParser;
        log.info("ModelParser initialized");
    }

    public void setMetricsSender(MetricsSender metricsSender) {
        this.metricsSender = metricsSender;
        log.info("MetricsSender initialized");
    }
}
