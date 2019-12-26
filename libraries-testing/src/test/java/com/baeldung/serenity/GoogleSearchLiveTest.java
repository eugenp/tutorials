package com.baeldung.serenity;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Managed;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

@RunWith(SerenityRunner.class)
public class GoogleSearchLiveTest {

    @Managed(driver = "chrome")
    private WebDriver browser;

    @Test
    public void whenGoogleBaeldungThenShouldSeeEugen() {
        browser.get("https://www.google.com/ncr");

        browser.findElement(By.name("q")).sendKeys("baeldung", Keys.ENTER);

        new WebDriverWait(browser, 5).until(visibilityOfElementLocated(By.cssSelector("._ksh")));

        assertThat(browser.findElement(By.cssSelector("._ksh")).getText(), containsString("Eugen (Baeldung)"));
    }

}
