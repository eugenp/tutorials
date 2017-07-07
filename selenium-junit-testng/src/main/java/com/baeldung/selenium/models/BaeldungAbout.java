package main.java.com.baeldung.selenium.models;

import main.java.com.baeldung.selenium.config.SeleniumConfig;
import main.java.com.baeldung.selenium.pages.BaeldungAboutPage;
import org.openqa.selenium.support.PageFactory;

public class BaeldungAbout {

    private SeleniumConfig config;

    public BaeldungAbout(SeleniumConfig config) {
        this.config = config;
        PageFactory.initElements(config.getDriver(), BaeldungAboutPage.class);
    }

    public void navigateTo() {
        config.navigateTo("http://www.baeldung.com/about/");
    }

    public String getPageTitle() {
        return BaeldungAboutPage.title.getText();
    }
}
