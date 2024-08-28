package com.baeldung.selenium.select;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SeleniumSelectLiveTest {

    private WebDriver driver;

    private static final String URL = "https://www.baeldung.com/contact";
    private static final String INPUT_XPATH = "//select[@name='question-recipient']";
    private static final String OPTION_XPATH = "//select[@name='question-recipient']/option[@value='Bug reporting']";
    private static final String OPTION_TEXT = "Bug reporting";
    private static final int OPTION_INDEX =  4;
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
    public void givenBaeldungContactPage_whenSelectQuestion_thenContainsOptionBugs() {
        driver.get(URL);
        WebElement inputElement = driver.findElement(By.xpath(OPTION_XPATH));
        assertEquals(OPTION_TEXT, inputElement.getText());
    }

    @Test
    public void givenBaeldungContactPage_whenSelectQuestion_thenSelectOptionBugsByValue() {
        driver.get(URL);
        WebElement inputElement = driver.findElement(By.xpath(INPUT_XPATH));
        WebElement optionBug = driver.findElement(By.xpath(OPTION_XPATH));

        Select select = new Select(inputElement);
        select.selectByValue(OPTION_TEXT);    
        assertTrue(optionBug.isSelected());
    }

    @Test
    public void givenBaeldungContactPage_whenSelectQuestion_thenSelectOptionBugsByText() {
        driver.get(URL);
        WebElement inputElement = driver.findElement(By.xpath(INPUT_XPATH));
        WebElement optionBug = driver.findElement(By.xpath(OPTION_XPATH));

        Select select = new Select(inputElement);
        select.selectByVisibleText(OPTION_TEXT);    
        assertTrue(optionBug.isSelected());
    }

    @Test
    public void givenBaeldungContactPage_whenSelectQuestion_thenSelectOptionBugsByIndex() {
        driver.get(URL);
        WebElement inputElement = driver.findElement(By.xpath(INPUT_XPATH));
        WebElement optionBug = driver.findElement(By.xpath(OPTION_XPATH));

        Select select = new Select(inputElement);
        select.selectByIndex(OPTION_INDEX);    
        assertTrue(optionBug.isSelected());
    }

}
