import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class GenericExample {

    public static void main(String[] args) {

        WebDriverManager.getInstance(ChromeDriver.class).setup();

        WebDriver driver = new ChromeDriver();

        driver.get("https://example.com");

        driver.quit();
    }
}