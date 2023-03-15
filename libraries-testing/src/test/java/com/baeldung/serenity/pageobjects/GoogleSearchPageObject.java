package com.baeldung.serenity.pageobjects;

import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

@DefaultUrl("https://www.google.com/ncr")
public class GoogleSearchPageObject extends PageObject {

    /* The selectors "q", "._ksh" and "button[id='L2AGLb'] div[role='none']" must be appropriate for your own context.
       you can inspect the desired element on the web page you are testing to get the appropriate selector */

    @FindBy(name = "q")
    private WebElement search;

    @FindBy(css = "._ksh")
    private WebElement result;

    @FindBy(css = "button[id='L2AGLb'] div[role='none']")
    private WebElement validateCookies;

    public void searchFor(String keyword) {
        search.sendKeys(keyword, Keys.ENTER);
    }

    public void resultMatches(String expected) {
        withTimeoutOf(5, SECONDS).waitFor(result).waitUntilVisible();
        assertThat(result.getText(), containsString(expected));
    }

    public void validateCookies() {
        validateCookies.click();
    }
}
