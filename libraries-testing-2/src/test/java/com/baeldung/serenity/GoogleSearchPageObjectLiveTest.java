package com.baeldung.serenity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import com.baeldung.serenity.pageobjects.GoogleSearchPageObject;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Managed;

@RunWith(SerenityRunner.class)
public class GoogleSearchPageObjectLiveTest {

    @Managed(driver = "chrome")
    private WebDriver browser;

    private GoogleSearchPageObject googleSearch;

    @Test
    public void whenGoogleBaeldungThenShouldSeeEugen() {
        googleSearch.open();

        // If your browser displays cookie settings dialog, uncomment the line below
        // googleSearch.validateCookies();

        googleSearch.searchFor("baeldung");

        googleSearch.resultMatches("Eugen (Baeldung)");
    }

}
