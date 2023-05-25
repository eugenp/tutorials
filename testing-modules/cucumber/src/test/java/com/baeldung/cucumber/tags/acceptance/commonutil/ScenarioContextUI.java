package com.baeldung.cucumber.tags.acceptance.commonutil;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;

import static io.github.bonigarcia.wdm.WebDriverManager.getInstance;
import static io.github.bonigarcia.wdm.config.DriverManagerType.CHROME;

@Component
@Scope(scopeName = "cucumber-glue")
public class ScenarioContextUI {

    protected static final String RANDOM_NUMBER_URL = "/random-number-generator";

    @LocalServerPort
    int port;
    private WebDriver driver;
    private ScenarioReport report;

    public ScenarioContextUI() {
        reset();
    }

    private void reset() {
        report = new ScenarioReport();
        driver = null;
    }

    private static WebDriver getRemoteWebDriver(URL url) {
        return new RemoteWebDriver(url, new DesiredCapabilities("chrome", "", Platform.ANY));
    }

    private static WebDriver getLocalChromeDriver() {
        getInstance(CHROME).setup();
        return new ChromeDriver();
    }

    public ScenarioReport getReport() {
        return report;
    }

    public String getRandomNumberUrl() {
        return "http://" + getServiceBaseUrl() + RANDOM_NUMBER_URL;
    }

    private String getServiceBaseUrl() {
        return CucumberEnvironment.getServiceHost() + ":" + Integer.toString(port);
    }

    /**
     * If we are running inside docker (mostly for gitlab ci purposes), we expect a selenium grid setup.
     * If that environment variable isn't set, we assume we're in "dev mode" and ChromeDriverManager will
     * provide the local instance of chromedriver (no need to have chromedriver installed).
     */
    public WebDriver getWebDriver() {
        if (driver == null) {
            return getFreshWebdriver();
        } else {
            return driver;
        }
    }

    private WebDriver getFreshWebdriver() {
        driver = CucumberEnvironment.getSeleniumGridUrl()
          .map(ScenarioContextUI::getRemoteWebDriver)
          .orElseGet(ScenarioContextUI::getLocalChromeDriver);
        return driver;
    }

}
