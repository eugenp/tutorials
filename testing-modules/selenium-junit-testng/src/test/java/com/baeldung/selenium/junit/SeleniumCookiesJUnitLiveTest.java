package test.java.com.baeldung.selenium.junit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SeleniumCookiesJUnitLiveTest {

    private WebDriver driver;
    private String navUrl;

    @Before
    public void setUp() {
        Capabilities capabilities = DesiredCapabilities.firefox();
        driver = new FirefoxDriver(capabilities);
        navUrl = "https://baeldung.com";
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
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

        lpCookie = new Cookie("lp_120073", "foo");
        driver.manage().addCookie(lpCookie);

        assertThat(lpCookie.getValue(), equalTo("foo"));
    }

}
