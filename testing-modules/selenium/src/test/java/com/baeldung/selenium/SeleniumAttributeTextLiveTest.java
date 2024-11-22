package com.baeldung.selenium;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;


public class SeleniumAttributeTextLiveTest {

    private WebDriver driver;

    private static final String URL = "https://www.baeldung.com/contact";
    private static final String LABEL_XPATH = "//label[contains(text(),'Your Name')]";
    private static final String LABEL_TEXT = "Your Name*";

    private static final String INPUT_XPATH = "//label[contains(text(),'Your Name')]//input";
    private static final String INPUT_NAME = "your-name";
    private static final String INPUT_LENGTH = "400";

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
    public void givenBaeldungContactPage_whenFoundLabel_thenContainsText() {
        driver.get(URL);
        WebElement inputElement = driver.findElement(By.xpath(LABEL_XPATH));
        assertEquals(LABEL_TEXT, inputElement.getText());
    }

    @Test
    public void givenBaeldungContactPage_whenFoundNameInput_thenContainsText() {
        driver.get(URL);
        WebElement inputElement = driver.findElement(By.xpath(INPUT_XPATH));
        assertEquals("", inputElement.getText());
    }

    @Test
    public void givenBaeldungContactPage_whenFoundNameInput_thenHasAttributeName() {
        driver.get(URL);
        WebElement inputElement = driver.findElement(By.xpath(INPUT_XPATH));
        assertEquals(INPUT_NAME, inputElement.getAttribute("name"));
    }

    @Test
    public void givenBaeldungContactPage_whenFoundNameInput_thenHasAttributeMaxlength() {
        driver.get(URL);
        WebElement inputElement = driver.findElement(By.xpath(INPUT_XPATH));
        assertEquals(INPUT_LENGTH, inputElement.getAttribute("maxlength"));
    }

    @Test
    public void givenBaeldungContactPage_whenFoundNameInput_thenHasNoAttributeX() {
        driver.get(URL);
        WebElement inputElement = driver.findElement(By.xpath(INPUT_XPATH));
        assertNull(inputElement.getAttribute("X"));
    }

}
