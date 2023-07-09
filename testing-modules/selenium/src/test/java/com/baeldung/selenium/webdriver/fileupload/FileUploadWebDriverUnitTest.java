package com.baeldung.selenium.webdriver.fileupload;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FileUploadWebDriverUnitTest {

    private WebDriver driver;

    private static final String URL = "http://www.csm-testcenter.org/test?do=show&subdo=common&test=file_upload";
    private static final String INPUT_NAME = "file_upload";

    @BeforeEach
    public void setUp() {
        WebDriverManager.firefoxdriver()
          .setup();
        driver = new FirefoxDriver();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void givenFileUploadPage_whenInputFilePath_thenFileUploadEndsWithFilename() {
        driver.get(URL);

        String filePath = System.getProperty("user.dir") + "/1688web.png";
        WebElement inputElement = driver.findElement(By.name(INPUT_NAME));
        WebElement submitButton = driver.findElement(By.name("http_submit"));

        inputElement.sendKeys(filePath);

        String actualFilePath = inputElement.getAttribute("value");
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);

        submitButton.click();

        Assert.assertTrue(actualFilePath.endsWith(fileName));
    }

}