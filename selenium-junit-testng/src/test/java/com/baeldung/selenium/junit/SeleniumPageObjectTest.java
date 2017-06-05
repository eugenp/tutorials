package test.java.com.baeldung.selenium.junit;

import main.java.com.baeldung.selenium.config.SeleniumConfig;
import main.java.com.baeldung.selenium.pages.BaeldungHomePage;
import main.java.com.baeldung.selenium.pages.StartHerePage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SeleniumPageObjectTest {

    private SeleniumConfig config;
    private BaeldungHomePage homePage;

    @Before
    public void setUp() {
        config = new SeleniumConfig();
        homePage = new BaeldungHomePage(config);
    }

    @After
    public void teardown() {
        config.close();
    }

    @Test
    public void givenHomePage_whenNavigate_thenTitleMatch() {
        homePage.navigate();
        assertThat(homePage.getPageTitle(), is("Baeldung"));
    }

    @Test
    public void givenHomePage_whenNavigate_thenShouldBeInStartHere() {
        homePage.navigate();
        StartHerePage startHerePage = homePage.clickOnStartHere();
        assertThat(startHerePage.getPageTitle(), is("Start Here"));
    }
}
