package test.java.com.baeldung.selenium.testng;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import main.java.com.baeldung.selenium.SeleniumExample;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class SeleniumWithTestNGLiveTest {

    private SeleniumExample seleniumExample;
    private String expecteTilteAboutBaeldungPage = "About Baeldung | Baeldung";

    @BeforeSuite
    public void setUp() {
        seleniumExample = new SeleniumExample();
    }

    @AfterSuite
    public void tearDown() {
        seleniumExample.closeWindow();
    }

    @Test
    public void whenAboutBaeldungIsLoaded_thenAboutEugenIsMentionedOnPage() {
        try {
            seleniumExample.getAboutBaeldungPage();
            String actualTitle = seleniumExample.getTitle();
            assertNotNull(actualTitle);
            assertEquals(actualTitle, expecteTilteAboutBaeldungPage);
            assertTrue(seleniumExample.isAuthorInformationAvailable());
        } catch (Exception exception) {
            exception.printStackTrace();
            seleniumExample.closeWindow();
        }
    }
}
