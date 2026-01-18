package com.baeldung.selenium.hiddeninput;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.baeldung.selenium.hiddeninput.DemoController;

class DemoControllerUnitTest {

    private DemoController demoController;
    private WebDriver driver;
    private WebElement mockElement;

    @BeforeEach
    void setUp() {
        demoController = new DemoController();
        driver = mock(WebDriver.class);
        mockElement = mock(WebElement.class);
    }

    @Test
    void givenDemoController_whenShowFormIsCalled_thenReturnsIndexView() {
        String viewName = demoController.showForm();
        assertEquals("index", viewName, "The view name should match our index.html template");
    }

    @Test
    void givenFormInputs_whenHandleSubmitIsCalled_thenRedirectsToResult() {
        String result = demoController.handleSubmit("test_user", "pass123", "male", "2025-08-15", "extra-data");
        assertEquals("redirect:/result.html", result);
    }

    @Test
    void givenWebDriver_whenFindingUsernameField_thenDriverReturnsWebElement() {
        when(driver.findElement(By.id("username"))).thenReturn(mockElement);
        WebElement usernameField = driver.findElement(By.id("username"));
        verify(driver, times(1)).findElement(By.id("username"));
        assertEquals(mockElement, usernameField);
    }
}