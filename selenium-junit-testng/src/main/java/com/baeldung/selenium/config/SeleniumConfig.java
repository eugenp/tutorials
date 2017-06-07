package main.java.com.baeldung.selenium.config;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
<<<<<<< HEAD
import java.util.regex.Matcher;
import java.util.regex.Pattern;
=======
>>>>>>> 0053a80758de714cc461ca30bf827bc6808397ec

public class SeleniumConfig {

    private WebDriver driver;

    public SeleniumConfig() {
        Capabilities capabilities = DesiredCapabilities.chrome();
        driver = new ChromeDriver(capabilities);
    }

    static {
        String osName = getOsName("os.name").toLowerCase();
        final Matcher matcher = Pattern.compile("(mac|nux|win)").matcher(osName);
        if (matcher.find()) {
            switch (matcher.group()) {
                case "mac":
                    System.setProperty("webdriver.chrome.driver", findFile("chromedriver.mac"));
                    break;
                case "nux":
                    System.setProperty("webdriver.chrome.driver", findFile("chromedriver.linux"));
                    break;
                case "win":
                    System.setProperty("webdriver.chrome.driver", findFile("chromedriver.exe"));
                    break;
            }
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
<<<<<<< HEAD

=======
>>>>>>> 0053a80758de714cc461ca30bf827bc6808397ec
}
