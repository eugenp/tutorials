package pageobject;

import pageobject.uiobjects.example.site.SiteJdi;
import org.testng.annotations.BeforeSuite;

import static com.epam.jdi.light.ui.html.PageFactory.initElements;

public interface TestsInit {
    @BeforeSuite(alwaysRun = true)
    static void setUp() {
        initElements(SiteJdi.class);
    }
}
