package com.baeldung.selenium.avoidbot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.mockito.Mockito.*;

class GoogleSearchServiceUnitTest {

    private WebDriver driver;
    private WebElement searchBox;
    private GoogleSearchService googleSearchService;

    @BeforeEach
    void setUp() {
        driver = Mockito.mock(WebDriver.class);
        searchBox = Mockito.mock(WebElement.class);
        when(driver.findElement(By.name("q"))).thenReturn(searchBox);
        googleSearchService = new GoogleSearchService(driver);
    }

    @Test
    void givenGoogleSearchService_whenNavigateToGoogle_thenDriverLoadsGoogleHomePage() {
        googleSearchService.navigateToGoogle();
        verify(driver).get("https://www.google.com");
    }

    @Test
    void givenGoogleSearchService_whenSearchWithQuery_thenSearchBoxReceivesQueryAndEnterKey() {
        googleSearchService.search("baeldung");
        verify(searchBox).sendKeys("baeldung");
        verify(searchBox).sendKeys(Keys.ENTER);
    }

    @Test
    void givenGoogleSearchService_whenGetPageTitle_thenReturnExpectedTitle() {
        when(driver.getTitle()).thenReturn("Google - Baeldung");
        String title = googleSearchService.getPageTitle();
        verify(driver).getTitle();
        assert title.equals("Google - Baeldung");
    }

    @Test
    void givenGoogleSearchService_whenQuit_thenDriverIsClosed() {
        googleSearchService.quit();
        verify(driver).quit();
    }
}

