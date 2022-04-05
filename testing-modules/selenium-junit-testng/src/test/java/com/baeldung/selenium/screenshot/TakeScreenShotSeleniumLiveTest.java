package com.baeldung.selenium.screenshot;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class TakeScreenShotSeleniumLiveTest {

    private static ChromeDriver driver;

    @BeforeClass
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", resolveResourcePath("chromedriver.mac"));

        Capabilities capabilities = DesiredCapabilities.chrome();
        driver = new ChromeDriver(capabilities);
        driver.manage()
            .timeouts()
            .implicitlyWait(5, TimeUnit.SECONDS);

        driver.get("http://www.google.com/");
    }

    @AfterClass
    public static void tearDown() throws IOException {
        driver.close();

        System.clearProperty("webdriver.chrome.driver");
    }

    @Test
    public void whenGoogleIsLoaded_thenCaptureScreenshot() throws IOException {
        takeScreenshot(resolveTestResourcePath("google-home.png"));

        assertTrue(new File(resolveTestResourcePath("google-home.png")).exists());
    }

    @Test
    public void whenGoogleIsLoaded_thenCaptureLogo() throws IOException {
        WebElement logo = driver.findElement(By.id("hplogo"));

        Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
            .coordsProvider(new WebDriverCoordsProvider())
            .takeScreenshot(driver, logo);

        ImageIO.write(screenshot.getImage(), "jpg", new File(resolveTestResourcePath("google-logo.png")));
        assertTrue(new File(resolveTestResourcePath("google-logo.png")).exists());
    }

    public void takeScreenshot(String pathname) throws IOException {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(src, new File(pathname));
    }

    private static String resolveResourcePath(String filename) {
        File file = new File("src/main/resources/" + filename);
        return file.getAbsolutePath();
    }

    private static String resolveTestResourcePath(String filename) {
        File file = new File("src/test/resources/" + filename);
        return file.getAbsolutePath();
    }
}
