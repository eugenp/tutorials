package com.baeldung.scrollelementintoview;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ScrollElementIntoViewUnitTest {

    private ScrollElementIntoView helper;
    private WebDriver driver;

    @BeforeAll
    void init() {
        helper = new ScrollElementIntoView();
        helper.setUp();
        driver = helper.getDriver();
    }

    @AfterAll
    void tearDown() {
        helper.tearDown();
    }

    @Test
    @DisplayName("Should scroll and fill First Name field")
    void givenFirstNameField_whenScrolledIntoView_thenFieldIsFilled() {
        WebElement firstName = driver.findElement(By.id("firstName"));
        helper.scrollToElementCenter(firstName);
        firstName.sendKeys("John");
        assertEquals("John", firstName.getAttribute("value"));
    }

    @Test
    @DisplayName("Should scroll and fill Middle Name field")
    void givenMiddleNameField_whenScrolledIntoView_thenFieldIsFilled() {
        WebElement middleName = driver.findElement(By.id("middleName"));
        helper.scrollToElementCenter(middleName);
        middleName.sendKeys("William");
        assertEquals("William", middleName.getAttribute("value"));
    }

    @Test
    @DisplayName("Should scroll and fill Last Name field")
    void givenLastNameField_whenScrolledIntoView_thenFieldIsFilled() {
        WebElement lastName = driver.findElement(By.id("lastName"));
        helper.scrollToElementCenter(lastName);
        lastName.sendKeys("Doe");
        assertEquals("Doe", lastName.getAttribute("value"));
    }
}
