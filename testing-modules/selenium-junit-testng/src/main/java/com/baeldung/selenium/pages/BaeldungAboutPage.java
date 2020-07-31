package com.baeldung.selenium.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BaeldungAboutPage {

    @FindBy(css = ".page-header > h1")
    public static WebElement title;
}
