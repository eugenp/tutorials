package com.baeldung.jmeter.java;

import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;

public class JMeterFromJmxUnitTest {

    @Test
    @Disabled("enable after adding link to jmeter installation")
    void startJMeterTest() throws Exception {

        StandardJMeterEngine standardJMeterEngine = new StandardJMeterEngine();

        JMeterUtils.loadJMeterProperties("path-to-jmeter-installation/bin/jmeter.properties");
        JMeterUtils.setJMeterHome("path-to-jmeter-installation");
        JMeterUtils.initLocale();

        SaveService.loadProperties();

        URL resource = getClass().getClassLoader()
            .getResource("test_plan.jmx");
        File file = new File(resource.toURI());
        HashTree hashTree = SaveService.loadTree(file);

        standardJMeterEngine.configure(hashTree);
        standardJMeterEngine.run();
    }

}
