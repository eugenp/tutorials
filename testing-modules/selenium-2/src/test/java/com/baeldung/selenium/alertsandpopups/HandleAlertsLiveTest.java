package com.baeldung.selenium.alertsandpopups;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HandleAlertsLiveTest {

    private WebDriver driver;

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void whenSimpleAlertIsTriggered_thenHandleIt() {
        driver.get("https://testpages.herokuapp.com/styled/alerts/alert-test.html");
        driver.findElement(By.id("alertexamples"))
            .click();

        Alert alert = driver.switchTo()
            .alert();
        String alertText = alert.getText();
        alert.accept();
        assertEquals("I am an alert box!", alertText);
    }

    @Test
    public void whenAlertIsNotHandled_thenThrowException() {
        driver.get("https://testpages.herokuapp.com/styled/alerts/alert-test.html");
        driver.findElement(By.id("alertexamples"))
            .click();

        assertThrows(UnhandledAlertException.class, () -> {
            driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/a[2]"))
                .click();
        });
    }

    @Test
    public void whenConfirmationAlertIsTriggered_thenHandleIt() {
        driver.get("https://testpages.herokuapp.com/styled/alerts/alert-test.html");
        driver.findElement(By.id("confirmexample"))
            .click();

        Alert alert = driver.switchTo()
            .alert();
        String alertText = alert.getText();
        alert.accept();
        assertEquals("true", driver.findElement(By.id("confirmreturn"))
            .getText());

        driver.findElement(By.id("confirmexample"))
            .click();
        alert = driver.switchTo()
            .alert();
        alert.dismiss();
        assertEquals("false", driver.findElement(By.id("confirmreturn"))
            .getText());
    }

    @Test
    public void whenPromptAlertIsTriggered_thenHandleIt() {
        driver.get("https://testpages.herokuapp.com/styled/alerts/alert-test.html");
        driver.findElement(By.id("promptexample"))
            .click();

        Alert alert = driver.switchTo()
            .alert();
        String inputText = "Selenium Test";
        alert.sendKeys(inputText);
        alert.accept();
        assertEquals(inputText, driver.findElement(By.id("promptreturn"))
            .getText());
    }

    @Test
    public void whenAlertIsPresent_thenWaitAndHandle() {
        driver.get("https://testpages.herokuapp.com/styled/alerts/alert-test.html");
        driver.findElement(By.id("alertexamples"))
            .click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10), Duration.ofMillis(500));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        alert.accept();
        assertEquals("I am an alert box!", alertText);
    }

    @Test
    public void whenNoAlertIsPresent_thenHandleGracefully() {
        driver.get("https://testpages.herokuapp.com/styled/alerts/alert-test.html");

        boolean noAlertExceptionThrown = false;
        try {
            Alert alert = driver.switchTo()
                .alert();
            alert.accept();
        } catch (NoAlertPresentException e) {
            noAlertExceptionThrown = true;
        }

        assertTrue(noAlertExceptionThrown, "NoAlertPresentException should be thrown");
        assertTrue(driver.getTitle()
            .contains("Alerts"), "Page title should contain 'Alerts'");
    }

}
