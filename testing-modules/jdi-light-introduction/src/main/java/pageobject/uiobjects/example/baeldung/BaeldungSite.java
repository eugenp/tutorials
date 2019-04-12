package pageobject.uiobjects.example.baeldung;

import com.epam.jdi.light.elements.pageobjects.annotations.JSite;
import pageobject.uiobjects.example.baeldung.pages.ContactPage;
import pageobject.uiobjects.example.baeldung.pages.HomePage;

@JSite("http://www.baeldung.com/")
public class BaeldungSite {
    public static HomePage homePage;
    public static ContactPage contactPage;
}
