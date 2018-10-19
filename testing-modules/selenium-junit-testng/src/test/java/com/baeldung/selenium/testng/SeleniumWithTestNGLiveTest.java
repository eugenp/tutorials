package test.java.com.baeldung.selenium.testng;

import main.java.com.baeldung.selenium.SeleniumExample;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class SeleniumWithTestNGLiveTest {

    private SeleniumExample seleniumExample;
    private String expectedTitle = "Baeldung | Java, Spring and Web Development tutorials";

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
        seleniumExample.getAboutBaeldungPage();
        String actualTitle = seleniumExample.getTitle();
        assertNotNull(actualTitle);
        assertEquals(expectedTitle, actualTitle);
        assertTrue(seleniumExample.isAuthorInformationAvailable());
    }
}
