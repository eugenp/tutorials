package com.baeldung.handlebars;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Showcases the built-in template helpers.
 *
 * @author isaolmez
 */
public class BuiltinHelperUnitTest {

    private TemplateLoader templateLoader = new ClassPathTemplateLoader("/handlebars", ".html");

    @Test
    public void whenUsedWith_ThenContextChanges() throws IOException {
        Handlebars handlebars = new Handlebars(templateLoader);
        Template template = handlebars.compile("with");
        Person person = getPerson("Baeldung");
        person.getAddress().setStreet("World");

        String templateString = template.apply(person);

        assertThat(templateString).contains("<h4>I live in World</h4>");
    }

    @Test
    public void whenUsedWithMustacheStyle_ThenContextChanges() throws IOException {
        Handlebars handlebars = new Handlebars(templateLoader);
        Template template = handlebars.compile("with_mustache");
        Person person = getPerson("Baeldung");
        person.getAddress().setStreet("World");

        String templateString = template.apply(person);

        assertThat(templateString).contains("<h4>I live in World</h4>");
    }

    @Test
    public void whenUsedEach_ThenIterates() throws IOException {
        Handlebars handlebars = new Handlebars(templateLoader);
        Template template = handlebars.compile("each");
        Person person = getPerson("Baeldung");
        Person friend1 = getPerson("Java");
        Person friend2 = getPerson("Spring");
        person.getFriends().add(friend1);
        person.getFriends().add(friend2);

        String templateString = template.apply(person);

        assertThat(templateString)
          .contains("<span>Java is my friend.</span>", "<span>Spring is my friend.</span>");
    }

    @Test
    public void whenUsedEachMustacheStyle_ThenIterates() throws IOException {
        Handlebars handlebars = new Handlebars(templateLoader);
        Template template = handlebars.compile("each_mustache");
        Person person = getPerson("Baeldung");
        Person friend1 = getPerson("Java");
        Person friend2 = getPerson("Spring");
        person.getFriends().add(friend1);
        person.getFriends().add(friend2);

        String templateString = template.apply(person);

        assertThat(templateString)
          .contains("<span>Java is my friend.</span>", "<span>Spring is my friend.</span>");
    }

    @Test
    public void whenUsedIf_ThenPutsCondition() throws IOException {
        Handlebars handlebars = new Handlebars(templateLoader);
        Template template = handlebars.compile("if");
        Person person = getPerson("Baeldung");
        person.setBusy(true);

        String templateString = template.apply(person);

        assertThat(templateString).contains("<h4>Baeldung is busy.</h4>");
    }

    @Test
    public void whenUsedIfMustacheStyle_ThenPutsCondition() throws IOException {
        Handlebars handlebars = new Handlebars(templateLoader);
        Template template = handlebars.compile("if_mustache");
        Person person = getPerson("Baeldung");
        person.setBusy(true);

        String templateString = template.apply(person);

        assertThat(templateString).contains("<h4>Baeldung is busy.</h4>");
    }

    private Person getPerson(String name) {
        Person person = new Person();
        person.setName(name);
        return person;
    }
}
