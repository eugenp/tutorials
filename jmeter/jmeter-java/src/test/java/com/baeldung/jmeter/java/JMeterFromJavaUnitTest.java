package com.baeldung.jmeter.java;

import java.io.File;
import java.net.URL;

import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.extractor.JSR223PostProcessor;
import org.apache.jmeter.protocol.http.sampler.HTTPSampler;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.SetupThreadGroup;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

class JMeterFromJavaUnitTest {

    private WireMockServer wireMockServer = new WireMockServer(new WireMockConfiguration().port(8080));

    @BeforeEach
    void setUp() {
        wireMockServer.start();
        WireMock.configureFor("localhost", 8080);
        WireMock.stubFor(WireMock.get("/baeldung")
            .willReturn(WireMock.aResponse()
                .withBody("Hello from baeldung server.")
                .withStatus(200)));
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    @Disabled("enable after adding link to jmeter installation")
    void runExistingJMeterTest() throws Exception {
        StandardJMeterEngine standardJMeterEngine = new StandardJMeterEngine();

        JMeterUtils.loadJMeterProperties("path-to-jmeter-installation/bin/jmeter.properties");
        JMeterUtils.setJMeterHome("path-to-jmeter-installation/apache-jmeter-5.3");
        JMeterUtils.initLocale();

        SaveService.loadProperties();

        URL resource = getClass().getClassLoader()
            .getResource("test_plan.jmx");
        File file = new File(resource.toURI());
        HashTree hashTree = SaveService.loadTree(file);

        standardJMeterEngine.configure(hashTree);
        standardJMeterEngine.run();
    }

    @Test
    void buildJMeterWithJavaTest() {
        StandardJMeterEngine standardJMeterEngine = new StandardJMeterEngine();

        URL resource = getClass().getClassLoader()
            .getResource("jmeter.properties");
        JMeterUtils.loadJMeterProperties(resource.getPath());
        JMeterUtils.initLocale();

        HTTPSampler httpSampler = new HTTPSampler();
        httpSampler.setDomain("localhost");
        httpSampler.setPort(8080);
        httpSampler.setPath("/baeldung");
        httpSampler.setMethod("GET");

        JSR223PostProcessor jsr223PostProcessor = new JSR223PostProcessor();
        jsr223PostProcessor.setProperty("scriptLanguage", "groovy");
        jsr223PostProcessor.setProperty("script", "log.info(\"Request: \" + prev.getUrlAsString() + " + "\" Reponse: \" + prev.getResponseCode() +  \" \" + prev.getResponseDataAsString());");

        LoopController loopController = new LoopController();
        loopController.setLoops(2);
        loopController.addTestElement(httpSampler);
        loopController.setFirst(true);
        loopController.initialize();

        SetupThreadGroup setupThreadGroup = new SetupThreadGroup();
        setupThreadGroup.setNumThreads(2);
        setupThreadGroup.setRampUp(1);
        setupThreadGroup.setSamplerController(loopController);

        TestPlan testPlan = new TestPlan("My Test Plan");
        HashTree testPlanTree = new HashTree();

        HashTree threadGroupTree = testPlanTree.add(testPlan, setupThreadGroup);
        threadGroupTree.add(loopController);
        threadGroupTree.add(httpSampler, jsr223PostProcessor);

        standardJMeterEngine.configure(testPlanTree);
        standardJMeterEngine.run();
    }
}
