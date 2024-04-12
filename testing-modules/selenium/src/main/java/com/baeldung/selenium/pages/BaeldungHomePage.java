package com.baeldung.selenium.pages;

import com.baeldung.selenium.config.SeleniumConfig;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BaeldungHomePage {

    private SeleniumConfig config;
    @FindBy(css = ".nav--logo_mobile")
    private WebElement title;
    @FindBy(css = ".header--menu")
    private WebElement startHere;

    public BaeldungHomePage(SeleniumConfig config) {
        this.config = config;
        PageFactory.initElements(this.config.getDriver(), this);
    }

    public void navigate() {
        this.config.navigateTo("http://www.baeldung.com/");
    }

    public String getPageTitle() {
        return title.getAttribute("title");
    }

    public StartHerePage clickOnStartHere() {
        config.clickElement(startHere);

        StartHerePage startHerePage = new StartHerePage(config);
        PageFactory.initElements(config.getDriver(), startHerePage);

        return startHerePage;
    }
}
