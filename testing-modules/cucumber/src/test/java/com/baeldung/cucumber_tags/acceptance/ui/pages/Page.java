package com.baeldung.cucumber_tags.acceptance.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class Page {
    private static final long DEFAULT_WAIT_SECONDS = 5;
    private static Page currentPage;
    final WebDriver driver;

    Page(WebDriver driver, String title) {
        currentPage = this;
        this.driver = driver;
        getWait().until(titleIs(title));
    }

    public static <T extends Page> T getPage(Class<T> pageClass) {
        return pageClass.cast(checkNotNull(currentPage));
    }

    WebDriverWait getWait() {
        return new WebDriverWait(driver, DEFAULT_WAIT_SECONDS);
    }

}
