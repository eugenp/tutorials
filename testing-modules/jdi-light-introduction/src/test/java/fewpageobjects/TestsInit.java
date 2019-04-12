package fewpageobjects;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import pageobject.uiobjects.example.baeldung.BaeldungSite;
import pageobject.uiobjects.example.site.SiteJdi;

import static com.epam.jdi.light.driver.WebDriverUtils.killAllSeleniumDrivers;
import static com.epam.jdi.light.logger.LogLevels.STEP;
import static com.epam.jdi.light.settings.WebSettings.logger;
import static com.epam.jdi.light.ui.html.PageFactory.initElements;
import static pageobject.uiobjects.example.baeldung.BaeldungSite.homePage;

public interface TestsInit {
    @BeforeSuite(alwaysRun = true)
    static void setUp() {
        logger.setLogLevel(STEP);
        initElements(BaeldungSite.class);
        homePage.open();
    }
    @AfterSuite(alwaysRun = true)
    static void teardown() {
        killAllSeleniumDrivers();
    }
}
