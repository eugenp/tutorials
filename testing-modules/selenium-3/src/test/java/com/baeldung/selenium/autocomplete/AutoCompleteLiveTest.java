package com.baeldung.selenium.autocomplete;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AutoCompleteLiveTest {

    private static final String XPATH_INPUT = "//div[@id='gh-search-box']//input";
    private static final String XPATH_AUTOCOMPLETE_ITEMS = "//ul[@id='ebay-autocomplete']/li";

    private WebDriver driver;

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void whenUserNavigatesToEBays_thenSelectThe2ndAutoCompleteItemFromSearchInput() throws Exception {
        driver.navigate().to("https://www.ebay.com");

        WebElement inputElement = driver.findElement(By.xpath(XPATH_INPUT));
        String text = "iphone";
        for (char c : text.toCharArray()) {
            inputElement.sendKeys(Character.toString(c));
            Thread.sleep(50);
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        List<WebElement> autoCompleteElements = wait.until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath(XPATH_AUTOCOMPLETE_ITEMS)
            )
        );

        assertThat(autoCompleteElements.size()).isGreaterThanOrEqualTo(2);

        WebElement secondItem = autoCompleteElements.get(1);
        secondItem.click();
    }

}