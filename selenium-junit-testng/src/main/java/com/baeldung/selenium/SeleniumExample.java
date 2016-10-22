package main.java.com.baeldung.selenium;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumExample {

    private WebDriver webDriver;
    private String url = "http://www.baeldung.com/";

    public SeleniumExample() {
        webDriver = new FirefoxDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.get(url);
    }

    public void closeWindow() {
        webDriver.close();
    }

    public String getTitle() {
        return webDriver.getTitle();
    }

    public void getAboutBaeldungPage() {
        closeOverlay();
        clickAboutLink();
        clickAboutUsLink();
    }

    private void closeOverlay() {
        List<WebElement> webElementList = webDriver.findElements(By.tagName("a"));
        if (webElementList != null && !webElementList.isEmpty()) {
            webElementList.stream().filter(webElement -> "Close".equalsIgnoreCase(webElement.getAttribute("title"))).findAny().get().click();
        }
    }

    private void clickAboutLink() {
        webDriver.findElement(By.partialLinkText("About")).click();
    }

    private void clickAboutUsLink() {
        webDriver.findElement(By.partialLinkText("About Baeldung.")).click();
    }

    public boolean isAuthorInformationAvailable() {
        return webDriver
                .findElement(By.xpath("//*[contains(text(), 'Eugen – an engineer')]"))
                .isDisplayed();
    }
}
