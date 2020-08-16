package com.baeldung.selenium.cookies;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.io.File;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class SeleniumCookiesJUnitLiveTest {

    private WebDriver driver;
    private String navUrl;

    @Before
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", findFile("geckodriver.mac"));
        
        Capabilities capabilities = DesiredCapabilities.firefox();
        driver = new FirefoxDriver(capabilities);
        navUrl = "https://baeldung.com";
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
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
        driver.navigate().to(navUrl);
        Set<Cookie> cookies = driver.manage().getCookies();

        assertThat(cookies, is(not(empty())));
    }

    @Test
    public void whenNavigate_thenLpCookieExists() {
        driver.navigate().to(navUrl);
        Cookie lpCookie = driver.manage().getCookieNamed("lp_120073");

        assertThat(lpCookie, is(not(nullValue())));
    }

    @Test
    public void whenNavigate_thenLpCookieIsHasCorrectValue() {
        driver.navigate().to(navUrl);
        Cookie lpCookie = driver.manage().getCookieNamed("lp_120073");

        assertThat(lpCookie.getValue(), containsString("www.baeldung.com"));
    }

    @Test
    public void whenNavigate_thenLpCookieHasCorrectProps() {
        driver.navigate().to(navUrl);
        Cookie lpCookie = driver.manage().getCookieNamed("lp_120073");

        assertThat(lpCookie.getDomain(), equalTo(".baeldung.com"));
        assertThat(lpCookie.getPath(), equalTo("/"));
        assertThat(lpCookie.getExpiry(), is(not(nullValue())));
        assertThat(lpCookie.isSecure(), equalTo(false));
        assertThat(lpCookie.isHttpOnly(), equalTo(false));
    }

    @Test
    public void whenAddingCookie_thenItIsPresent() {
        driver.navigate().to(navUrl);
        Cookie cookie = new Cookie("foo", "bar");
        driver.manage().addCookie(cookie);
        Cookie driverCookie = driver.manage().getCookieNamed("foo");

        assertThat(driverCookie.getValue(), equalTo("bar"));
    }

    @Test
    public void whenDeletingCookie_thenItIsAbsent() {
        driver.navigate().to(navUrl);
        Cookie lpCookie = driver.manage().getCookieNamed("lp_120073");

        assertThat(lpCookie, is(not(nullValue())));

        driver.manage().deleteCookie(lpCookie);
        Cookie deletedCookie = driver.manage().getCookieNamed("lp_120073");

        assertThat(deletedCookie, is(nullValue()));
    }

    @Test
    public void whenOverridingCookie_thenItIsUpdated() {
        driver.navigate().to(navUrl);
        Cookie lpCookie = driver.manage().getCookieNamed("lp_120073");
        driver.manage().deleteCookie(lpCookie);

        Cookie newLpCookie = new Cookie("lp_120073", "foo");
        driver.manage().addCookie(newLpCookie);

        Cookie overriddenCookie = driver.manage().getCookieNamed("lp_120073");

        assertThat(overriddenCookie.getValue(), equalTo("foo"));
    }

}
