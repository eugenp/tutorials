package com.baeldung.selenium.cookies;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.io.File;
import java.time.Duration;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumCookiesJUnitLiveTest {

    private WebDriver driver;
    private String navUrl;

    private final String COOKIE = "SNS";

    @Before
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", findFile("geckodriver.mac"));

        driver = new FirefoxDriver();
        navUrl = "https://baeldung.com";
        driver.navigate().to(navUrl);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(1000));
        wait.until(d -> d.manage().getCookieNamed(COOKIE) != null);
    }
    
    private static String findFile(String filename) {
        String[] paths = { "", "bin/", "target/classes" }; // if you have chromedriver somewhere else on the path, then put it here.
        for (String path : paths) {
            if (new File(path + filename).exists())
                return path + filename;
        }
        return "";
    }

    @After
    public void teardown() {
        driver.quit();
    }

    @Test
    public void whenNavigate_thenCookiesExist() {
        Set<Cookie> cookies = driver.manage().getCookies();

        assertThat(cookies, is(not(empty())));
    }

    @Test
    public void whenNavigate_thenLpCookieExists() {
        Cookie lpCookie = driver.manage().getCookieNamed(COOKIE);

        assertThat(lpCookie, is(not(nullValue())));
    }

    @Test
    public void whenNavigate_thenLpCookieIsHasCorrectValue() {
        Cookie lpCookie = driver.manage().getCookieNamed(COOKIE);

        assertThat(lpCookie.getValue(), containsString("1"));
    }

    @Test
    public void whenNavigate_thenLpCookieHasCorrectProps() {
        Cookie lpCookie = driver.manage().getCookieNamed(COOKIE);

        assertThat(lpCookie.getDomain(), equalTo("www.baeldung.com"));
        assertThat(lpCookie.getPath(), equalTo("/"));
        assertThat(lpCookie.isSecure(), equalTo(false));
        assertThat(lpCookie.isHttpOnly(), equalTo(false));
    }

    @Test
    public void whenAddingCookie_thenItIsPresent() {
        Cookie cookie = new Cookie("foo", "bar");
        driver.manage().addCookie(cookie);
        Cookie driverCookie = driver.manage().getCookieNamed("foo");

        assertThat(driverCookie.getValue(), equalTo("bar"));
    }

    @Test
    public void whenDeletingCookie_thenItIsAbsent() {
        Cookie lpCookie = driver.manage().getCookieNamed("SNS");

        assertThat(lpCookie, is(not(nullValue())));

        driver.manage().deleteCookie(lpCookie);
        Cookie deletedCookie = driver.manage().getCookieNamed(COOKIE);

        assertThat(deletedCookie, is(nullValue()));
    }

    @Test
    public void whenOverridingCookie_thenItIsUpdated() {
        Cookie lpCookie = driver.manage().getCookieNamed(COOKIE);
        driver.manage().deleteCookie(lpCookie);

        Cookie newLpCookie = new Cookie(COOKIE, "foo");
        driver.manage().addCookie(newLpCookie);

        Cookie overriddenCookie = driver.manage().getCookieNamed(COOKIE);

        assertThat(overriddenCookie.getValue(), equalTo("foo"));
    }

}