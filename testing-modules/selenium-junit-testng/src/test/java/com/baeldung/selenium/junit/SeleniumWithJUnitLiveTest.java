package test.java.com.baeldung.selenium.junit;

import main.java.com.baeldung.selenium.SeleniumExample;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.testng.Assert.*;

public class SeleniumWithJUnitLiveTest {

    private static SeleniumExample seleniumExample;
    private String expectedTitle = "Baeldung | Java, Spring and Web Development tutorials";

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
            seleniumExample.getAboutBaeldungPage();
            String actualTitle = seleniumExample.getTitle();
            assertNotNull(actualTitle);
            assertEquals(expectedTitle, actualTitle);
            assertTrue(seleniumExample.isAuthorInformationAvailable());
    }

}
