import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.htmlunit.HtmlUnitDriver

environments {

    // run via “./gradlew -Dgeb.env=chrome -Dwebdriver.chrome.driver=/Applications/chromedriver iT”
    chrome {
        driver = { new ChromeDriver() }
    }

    // run via “./gradlew -Dgeb.env=chromeHeadless -Dwebdriver.chrome.driver=/Applications/chromedriver iT”
    chromeHeadless {
        driver = {
            ChromeOptions o = new ChromeOptions()
            o.addArguments('headless')
            new ChromeDriver(o)
        }
    }

    // run via “./gradlew -Dgeb.env=htmlUnit iT”
    htmlUnit {
        driver = { new HtmlUnitDriver() }
    }
}
