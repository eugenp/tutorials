package pageobject.uiobjects.example.site;

import com.epam.jdi.light.elements.pageobjects.annotations.JSite;
import com.epam.jdi.light.elements.pageobjects.annotations.Title;
import com.epam.jdi.light.elements.pageobjects.annotations.Url;
import pageobject.uiobjects.example.site.pages.AboutPage;
import pageobject.uiobjects.example.site.pages.HomePage;

@JSite("https://www.baeldung.com/")
public class SiteJdi {
    @Url("/") public static HomePage homePage;
    @Url("/about/") public static AboutPage aboutPage;
}
