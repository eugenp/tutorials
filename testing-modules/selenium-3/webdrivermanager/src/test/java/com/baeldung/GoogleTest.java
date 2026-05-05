import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class GoogleTest {

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @Test
    void testGoogle() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.google.com");

        driver.quit();
    }
}