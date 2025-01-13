package com.baeldung.selenium.datepicker;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SeleniumDatePickerLiveTest {

    private WebDriver driver;

    private static final String URL = "https://demoqa.com/automation-practice-form";
    private static final String INPUT_XPATH = "//input[@id='dateOfBirthInput']";
    private static final String INPUT_TYPE = "text";
    private static final String INPUT_MONTH_XPATH = "//div[@class='react-datepicker__header']"
            + "//select[@class='react-datepicker__month-select']";
    private static final String INPUT_YEAR_XPATH = "//div[@class='react-datepicker__header']"
            + "//select[@class='react-datepicker__year-select']";
    private static final String INPUT_DAY_XPATH = "//div[contains(@class,\"react-datepicker__day\") and " 
            + "contains(@aria-label,\"December\") and text()=\"2\"]";

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
    public void givenDemoQAPage_whenFoundDateInput_thenContainsText() {
        driver.get(URL);
        WebElement inputElement = driver.findElement(By.xpath(INPUT_XPATH));
        assertEquals("", inputElement.getText());
    }

    @Test
    public void givenDemoQAPage_whenFoundDateInput_thenHasAttributeType() {
        driver.get(URL);
        WebElement inputElement = driver.findElement(By.xpath(INPUT_XPATH));
        assertEquals(INPUT_TYPE, inputElement.getAttribute("type"));
    }

    @Test
    public void givenDemoQAPage_whenSelectDate_thenHasCorrectDate() {
        driver.get(URL);
        WebElement inputElement = driver.findElement(By.xpath(INPUT_XPATH));
        inputElement.click();

        // Select Year
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        WebElement yearElement = driver.findElement(By.xpath(INPUT_YEAR_XPATH));
        wait.until(d -> yearElement.isDisplayed());
        Select selectYear = new Select(yearElement);
        selectYear.selectByVisibleText("2024");    
        
        // Select Month
        WebElement monthElement = driver.findElement(By.xpath(INPUT_MONTH_XPATH));
        wait.until(d -> monthElement.isDisplayed());
        Select selectMonth = new Select(monthElement);
        selectMonth.selectByVisibleText("December");
        final String selectOptionMonth = INPUT_MONTH_XPATH + "/option[text()='December']";
        WebElement optionDecember = driver.findElement(By.xpath(selectOptionMonth));

        // Select Day
        WebElement dayElement = driver.findElement(By.xpath(INPUT_DAY_XPATH));
        wait.until(d -> dayElement.isDisplayed());
        dayElement.click();

        // Check selected date value
        assertEquals("02 Dec 2024", inputElement.getAttribute("value"), "Wrong Date Selected");
    }

}
