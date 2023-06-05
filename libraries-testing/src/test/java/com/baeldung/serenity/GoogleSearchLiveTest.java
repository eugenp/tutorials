package com.baeldung.serenity;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Managed;

@RunWith(SerenityRunner.class)
public class GoogleSearchLiveTest {

    /* The selectors must be appropriate for your own context.
       you can inspect the desired element on the web page you are testing to get the appropriate selector */
    private static String SELECTOR_EUGEN_TEXT = ".haz7je";
    private static String SELECTOR_VALIDATE_COOKIES_DIALOG = "button[id='L2AGLb'] div[role='none']";

    @Managed(driver = "chrome")
    private WebDriver browser;

    @Test
    public void whenGoogleBaeldungThenShouldSeeEugen() {
        browser.get("https://www.google.com/ncr");

        // If your browser displays cookie settings dialog, uncomment the line below
        // browser.findElement(By.cssSelector(SELECTOR_VALIDATE_COOKIES_DIALOG)).click();

        browser.findElement(By.name("q")).sendKeys("baeldung", Keys.ENTER);

        new WebDriverWait(browser, 5).until(visibilityOfElementLocated(By.cssSelector(SELECTOR_EUGEN_TEXT)));

        assertThat(browser.findElement(By.cssSelector(SELECTOR_EUGEN_TEXT)).getText(), containsString("Eugen (Baeldung)"));
    }

}
