package com.baeldung.selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.baeldung.selenium.config.SeleniumConfig;

public class SeleniumExample {

    private SeleniumConfig config;
    private String url = "http://www.baeldung.com/";

    public SeleniumExample() {
        config = new SeleniumConfig();
        config.getDriver()
            .get(url);
    }

    public void closeWindow() {
        this.config.getDriver()
            .close();
    }

    public String getTitle() {
        return this.config.getDriver()
            .getTitle();
    }

    public void getAboutBaeldungPage() {
        closeOverlay();
        clickAboutLink();
        clickAboutUsLink();
    }

    private void closeOverlay() {
        List<WebElement> webElementList = this.config.getDriver()
            .findElements(By.tagName("a"));
        if (webElementList != null) {
            webElementList.stream()
                .filter(webElement -> "Close".equalsIgnoreCase(webElement.getAttribute("title")))
                .filter(WebElement::isDisplayed)
                .findAny()
                .ifPresent(WebElement::click);
        }
    }

    private void clickAboutLink() {
        Actions actions = new Actions(config.getDriver());
        WebElement aboutElement = this.config.getDriver()
            .findElement(By.id("menu-item-6138"));
        
        actions.moveToElement(aboutElement).perform();
    }

    private void clickAboutUsLink() {
        WebElement element = this.config.getDriver()
            .findElement(By.partialLinkText("About Baeldung."));
        element.click();
    }

    public boolean isAuthorInformationAvailable() {
        return this.config.getDriver()
            .getPageSource()
            .contains("Hey ! I'm Eugen");
    }

}
