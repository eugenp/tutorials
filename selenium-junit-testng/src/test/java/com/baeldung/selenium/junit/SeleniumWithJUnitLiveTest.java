package test.java.com.baeldung.selenium.junit;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import main.java.com.baeldung.selenium.SeleniumExample;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class SeleniumWithJUnitLiveTest {

    private static SeleniumExample seleniumExample;
    private String expecteTilteAboutBaeldungPage = "About Baeldung | Baeldung";

    @BeforeClass
    public static void setUp() {
        seleniumExample = new SeleniumExample();
    }

    @AfterClass
    public static void tearDown() {
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
