package java.com.baeldung.selenium.junit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertEquals;

public class SeleniumJavaScriptClickTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 5000);
    }

    @After
    public void cleanUp() {
        driver.close();
    }

    @Test
    public void should_search_for_selenium_articles() {
        driver.get("https://baeldung.com");
        String title = driver.getTitle();
        assertEquals("Baeldung | Java, Spring and Web Development tutorials", title);

        wait.until(ExpectedConditions.elementToBeClickable(By.className("menu-search")));
        WebElement searchButton = driver.findElement(By.className("menu-search"));
        clickElement(searchButton);

        WebElement searchInput = driver.findElement(By.id("search"));
        searchInput.sendKeys("Selenium");

        wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-search")));
        WebElement seeSearchResultsButton = driver.findElement(By.className("btn-search"));
        clickElement(seeSearchResultsButton);
    }

    private void clickElement(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }

}
