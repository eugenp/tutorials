package com.baeldung.cucumber_tags.acceptance.commonutil;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.config.LogConfig;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = {"acceptance-test"})
@AutoConfigureMockMvc
@CucumberContextConfiguration
public class ScenarioHooks {

    @Autowired
    private ScenarioContextUI uiContext;

    @Autowired
    private ScenarioContextApi apiContext;

    @Autowired
    private MockMvc mvc;

    @Before("@ui")
    public void setupForUI() {
        uiContext.getWebDriver();
    }

    @After("@ui")
    public void tearDownForUi(Scenario scenario) throws IOException {
        uiContext.getReport().write(scenario);
        uiContext.getReport().captureScreenShot(scenario, uiContext.getWebDriver());
        uiContext.getWebDriver().quit();
    }

    @Before("@api")
    public void setupForApi() {
        RestAssuredMockMvc.mockMvc(mvc);
        RestAssuredMockMvc.config = RestAssuredMockMvc.config()
          .logConfig(new LogConfig(
             apiContext.getReport().getRestLogPrintStream(),
               true));
    }

    @After("@api")
    public void tearDownForApi(Scenario scenario) throws IOException {
        apiContext.getReport().write(scenario);
    }
}
