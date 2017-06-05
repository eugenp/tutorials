package main.java.com.baeldung.selenium.config;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

public class SeleniumConfig {

    private WebDriver driver;

    public SeleniumConfig() {
        Capabilities capabilities = DesiredCapabilities.chrome();
        driver = new ChromeDriver(capabilities);
    }

    static {
        if (getOsName("os.name").toLowerCase().contains("mac")) {
            System.setProperty("webdriver.chrome.driver", findFile("chromedriver.mac"));
        } else if (getOsName("os.name").toLowerCase().contains("nix") ||
                getOsName("os.name").toLowerCase().contains("nux") ||
                getOsName("os.name").toLowerCase().contains("aix")) {
            System.setProperty("webdriver.chrome.driver", findFile("chromedriver.linux"));
        } else if (getOsName("os.name").toLowerCase().contains("win")) {
            System.setProperty("webdriver.chrome.driver", findFile("chromedriver.exe"));
        }
    }

    private static String getOsName(String prop) {
        return (System.getProperty(prop));
    }

    static private String findFile(String filename) {
        String paths[] = {"", "bin/", "target/classes"}; // if you have chromedriver somewhere else on the path, then put it here.
        for (String path : paths) {
            if (new File(path + filename).exists())
                return path + filename;
        }
        return "";
    }

    public void close() {
        driver.quit();
    }

    public void navigateTo(String url) {
        driver.navigate().to(url);
    }

    public void clickElement(WebElement element) {
        element.click();
    }

    public WebDriver getDriver() {
        return driver;
    }
}
