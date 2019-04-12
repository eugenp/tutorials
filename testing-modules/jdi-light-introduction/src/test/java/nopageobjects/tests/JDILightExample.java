package nopageobjects.tests;

import com.epam.jdi.light.elements.composite.WebPage;
import org.testng.annotations.Test;

import static com.epam.jdi.light.elements.composite.WebPage.*;
import static com.epam.jdi.light.ui.html.HtmlFactory.$;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class JDILightExample {
    @Test
    public void openBaeldung() {
        openUrl("http://www.baeldung.com/");
    }
    @Test
    public void openAboutPage() {
        openUrl("http://www.baeldung.com/");
        $(".menu-about a").click();
        $("//h3[contains(.,'About Baeldung')]").click();
        assertThat(getUrl(), is("https://www.baeldung.com/about/"));
        assertThat(getTitle(), is("About Baeldung | Baeldung"));
    }

    @Test
    public void actionsWithPage() {
        openUrl("http://www.baeldung.com/");
        getUrl();
        getTitle();
        back();
        forward();
        getHtml();
        refresh();
    }
}
