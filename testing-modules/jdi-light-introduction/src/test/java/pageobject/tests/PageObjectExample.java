package pageobject.tests;

import pageobject.TestsInit;
import org.testng.annotations.Test;

import static pageobject.uiobjects.example.site.SiteJdi.*;
import static pageobject.uiobjects.example.site.pages.HomePage.aboutBaeldung;
import static pageobject.uiobjects.example.site.pages.HomePage.menuAbout;

public class PageObjectExample implements TestsInit {
    @Test
    public void openPage() {
        homePage.open();
        menuAbout.click();
        aboutBaeldung.click();
        aboutPage.checkOpened();
    }
}
