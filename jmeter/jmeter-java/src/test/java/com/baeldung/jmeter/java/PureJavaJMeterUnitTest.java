package com.baeldung.jmeter.java;

import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.http.sampler.HTTPSampler;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.SetupThreadGroup;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.junit.jupiter.api.Test;

import java.net.URL;

public class PureJavaJMeterUnitTest {

    @Test
    void pureJavaTest() {

        StandardJMeterEngine standardJMeterEngine = new StandardJMeterEngine();

        URL resource = getClass().getClassLoader()
            .getResource("jmeter.properties");
        JMeterUtils.loadJMeterProperties(resource.getPath());
        JMeterUtils.initLocale();

        HTTPSampler httpSampler = new HTTPSampler();
        httpSampler.setDomain("localhost");
        httpSampler.setPort(8080);
        httpSampler.setPath("/login");
        httpSampler.setMethod("GET");

        LoopController loopController = new LoopController();
        loopController.setLoops(10);
        loopController.addTestElement(httpSampler);
        loopController.setFirst(true);
        loopController.initialize();

        SetupThreadGroup setupThreadGroup = new SetupThreadGroup();
        setupThreadGroup.setNumThreads(3);
        setupThreadGroup.setRampUp(1);
        setupThreadGroup.setSamplerController(loopController);

        TestPlan testPlan = new TestPlan("My Test Plan");

        HashTree testPlanHashTree = new HashTree();
        testPlanHashTree.add(testPlan);

        HashTree threadGroupHashTree = testPlanHashTree.add(testPlan, setupThreadGroup);
        threadGroupHashTree.add(httpSampler);
        threadGroupHashTree.add(loopController);

        standardJMeterEngine.configure(testPlanHashTree);
        standardJMeterEngine.run();

    }
}
