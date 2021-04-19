package com.baeldung.cucumber_tags.acceptance.commonutil;

import org.openqa.selenium.InvalidArgumentException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

public final class CucumberEnvironment {

    private static final String SELENIUM_GRID_URL_ENV_VAR = "SELENIUM_GRID_URL";
    private static final String SERVICE_HOSTNAME = "SERVICE_HOSTNAME";
    private static final String LOCALHOST = "localhost";

    private CucumberEnvironment() {
    }

    /**
     * Gets the network host where the service is running. When running while developing, this is going to be
     * localhost because webdriver and the test runner are going to be on the same machine. On gitlab-ci /
     * docker-compose though, they are going to be on separate hosts so webdriver needs to hit the
     * machine where the test runner is starting the service, not localhost.
     */
    public static String getServiceHost() {
        final Optional<String> serviceHostname = Optional.ofNullable(System.getenv(SERVICE_HOSTNAME));
        return serviceHostname.orElse(LOCALHOST);
    }

    /**
     * Gets the url where the selenium grid instance is. This is used for gitlab-ci / docker-compose setups.
     */
    public static Optional<URL> getSeleniumGridUrl() {
        final Optional<String> seleniumGridUrlString = Optional.ofNullable(System.getenv(SELENIUM_GRID_URL_ENV_VAR));
        return seleniumGridUrlString.map(CucumberEnvironment::parseSeleniumGridUrl);
    }

    private static URL parseSeleniumGridUrl(String seleniumGridUrlString) {
        try {
            return new URL(seleniumGridUrlString);
        } catch (MalformedURLException e) {
            throw new InvalidArgumentException(SELENIUM_GRID_URL_ENV_VAR + "env var is not a valid URL");
        }
    }
}
