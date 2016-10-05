package main.java.com.baeldung.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumExample {

    private WebDriver webDriver;
    private String url = "http://www.baeldung.com/";
    
    public SeleniumExample() {
        webDriver = new FirefoxDriver();
        webDriver.get(url);
    }

    public void closeWindow() {
        webDriver.close();
    }

    public String getTitle() {
        return webDriver.getTitle();
    }

}
