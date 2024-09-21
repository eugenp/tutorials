package com.baeldung.selenium.alertsandpopups;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HandlePopupsLiveTest {

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
    public void whenBasicAuthPopupAppears_thenBypassWithCredentials() {
        String username = "admin";
        String password = "admin";
        String url = "https://" + username + ":" + password + "@the-internet.herokuapp.com/basic_auth";

        driver.get(url);

        String bodyText = driver.findElement(By.tagName("body"))
            .getText();
        assertTrue(bodyText.contains("Congratulations! You must have the proper credentials."));
    }

    @Test
    public void whenModalDialogAppears_thenCloseIt() {
        driver.get("https://the-internet.herokuapp.com/entry_ad");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10), Duration.ofMillis(500));
        WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("modal")));
        WebElement closeElement = driver.findElement(By.xpath("//div[@class='modal-footer']/p"));

        closeElement.click();

        WebDriverWait modalWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean modalIsClosed = modalWait.until(ExpectedConditions.invisibilityOf(modal));

        assertTrue(modalIsClosed, "The modal should be closed after clicking the close button");
    }

}
