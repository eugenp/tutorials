package main.java.com.baeldung.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumExample {

    private WebDriver webDriver;
    private final String url = "http://www.baeldung.com/";
    private final String expectedTitle = "Baeldung | Java, Spring and Web Development tutorials";

    public SeleniumExample() {
        webDriver = new FirefoxDriver();
        webDriver.get(url);
    }

    public void closeWindow() {
        webDriver.close();
    }

    public String getActualTitle() {
        return webDriver.getTitle();
    }

    public String getExpectedTitle() {
        return expectedTitle;
    }

}
