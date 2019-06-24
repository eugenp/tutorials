package com.baeldung.handlebars;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import java.io.IOException;
import org.junit.Test;

/**
 * Showcases reusing the existing templates.
 *
 * @author isaolmez
 */
public class ReusingTemplatesUnitTest {

    private TemplateLoader templateLoader = new ClassPathTemplateLoader("/handlebars", ".html");

    @Test
    public void whenOtherTemplateIsReferenced_ThenCanReuse() throws IOException {
        Handlebars handlebars = new Handlebars(templateLoader);
        Template template = handlebars.compile("page");
        Person person = new Person();
        person.setName("Baeldung");

        String templateString = template.apply(person);

        assertThat(templateString).isEqualTo("<h4>Hi Baeldung!</h4>\n<p>This is the page Baeldung</p>");
    }

    @Test
    public void whenBlockIsDefined_ThenCanOverrideWithPartial() throws IOException {
        Handlebars handlebars = new Handlebars(templateLoader);
        Template template = handlebars.compile("simplemessage");
        Person person = new Person();
        person.setName("Baeldung");

        String templateString = template.apply(person);

        assertThat(templateString).isEqualTo("\n<html>\n"
                                               + "<body>\n"
                                               + "\n  This is the intro\n\n"
                                               + "\n  Hi there!\n\n"
                                               + "</body>\n"
                                               + "</html>");
    }
}
