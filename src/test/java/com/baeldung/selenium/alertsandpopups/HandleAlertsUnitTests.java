package com.baeldung.selenium.alertsandpopups;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

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

    @Test
    public void whenSimpleAlertIsTriggered_thenHandleIt() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://testpages.herokuapp.com/styled/alerts/alert-test.html");
        driver.findElement(By.id("alertexamples"))
            .click();

        Alert alert = driver.switchTo()
            .alert();
        String alertText = alert.getText();
        alert.accept();
        assertEquals("I am an alert box!", alertText);
        driver.quit();
    }

    @Test
    public void whenAlertIsNotHandled_thenThrowException() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://testpages.herokuapp.com/styled/alerts/alert-test.html");

        driver.findElement(By.id("alertexamples"))
            .click();

        assertThrows(UnhandledAlertException.class, () -> {
            driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/a[2]"))
                .click();
        });

        driver.quit();
    }

    @Test
    public void whenConfirmationAlertIsTriggered_thenHandleIt() {
        WebDriver driver = new ChromeDriver();
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
        driver.quit();
    }

    @Test
    public void whenPromptAlertIsTriggered_thenHandleIt() {
        WebDriver driver = new ChromeDriver();
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

        driver.quit();
    }

    @Test
    public void whenAlertIsPresent_thenWaitAndHandle() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://testpages.herokuapp.com/styled/alerts/alert-test.html");

        driver.findElement(By.id("alertexamples"))
            .click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        String alertText = alert.getText();
        alert.accept();

        assertEquals("I am an alert box!", alertText);

        driver.quit();
    }


    @Test
    public void whenNoAlertIsPresent_thenHandleGracefully() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://testpages.herokuapp.com/styled/alerts/alert-test.html");

        try {
            Alert alert = driver.switchTo()
                .alert();
            alert.accept();
        } catch (NoAlertPresentException e) {

        }

        assertTrue(driver.getTitle()
            .contains("Alerts"));

        driver.quit();
    }

}
