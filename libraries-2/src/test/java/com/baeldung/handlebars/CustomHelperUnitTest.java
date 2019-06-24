package com.baeldung.handlebars;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import java.io.IOException;
import org.junit.Test;

/**
 * Showcases implementing a custom template helper.
 *
 * @author isaolmez
 */
public class CustomHelperUnitTest {

    private TemplateLoader templateLoader = new ClassPathTemplateLoader("/handlebars", ".html");

    @Test
    public void whenHelperIsCreated_ThenCanRegister() throws IOException {
        Handlebars handlebars = new Handlebars(templateLoader);
        handlebars.registerHelper("isBusy", new Helper<Person>() {
            @Override
            public Object apply(Person context, Options options) throws IOException {
                String busyString = context.isBusy() ? "busy" : "available";
                return context.getName() + " - " + busyString;
            }
        });
        Template template = handlebars.compile("person");
        Person person = getPerson("Baeldung");

        String templateString = template.apply(person);

        assertThat(templateString).isEqualTo("Baeldung - busy");
    }

    @Test
    public void whenHelperSourceIsCreated_ThenCanRegister() throws IOException {
        Handlebars handlebars = new Handlebars(templateLoader);
        handlebars.registerHelpers(new HelperSource());
        Template template = handlebars.compile("person");
        Person person = getPerson("Baeldung");

        String templateString = template.apply(person);

        assertThat(templateString).isEqualTo("Baeldung - busy");
    }

    private Person getPerson(String name) {
        Person person = new Person();
        person.setName(name);
        person.setBusy(true);
        return person;
    }
}
