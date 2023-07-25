package com.baeldung.selenium.webdriver;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SeleniumWebDriverUnitTest {

    private WebDriver driver;

    private static final String URL = "https://duckduckgo.com/";
    private static final String INPUT_ID = "searchbox_input__bEGm3";

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }
    
    @AfterEach
    public void tearDown() {
        driver.quit();
    }
    
    @Test
    public void givenDuckDuckGoHomePage_whenInputHelloWorld_thenInputValueIsHelloWorld() {
        driver.get(URL);
        WebElement inputElement = driver.findElement(By.className(INPUT_ID));
        inputElement.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        inputElement.sendKeys("Hello World!");

        String inputValue = inputElement.getAttribute("value");
        Assert.assertEquals("Hello World!", inputValue);
    }

}
