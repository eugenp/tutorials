package com.baeldung.handlebars;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

/**
 * Showcases the tag usage and different template loading mechanisms.
 *
 * @author isaolmez
 */
public class BasicUsageUnitTest {

    @Test
    public void whenThereIsNoTemplateFile_ThenCompilesInline() throws IOException {
        Handlebars handlebars = new Handlebars();
        Template template = handlebars.compileInline("Hi {{this}}!");

        String templateString = template.apply("Baeldung");

        assertThat(templateString).isEqualTo("Hi Baeldung!");
    }

    @Test
    public void whenParameterMapIsSupplied_thenDisplays() throws IOException {
        Handlebars handlebars = new Handlebars();
        Template template = handlebars.compileInline("Hi {{name}}!");
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("name", "Baeldung");

        String templateString = template.apply(parameterMap);

        assertThat(templateString).isEqualTo("Hi Baeldung!");
    }

    @Test
    public void whenParameterObjectIsSupplied_ThenDisplays() throws IOException {
        Handlebars handlebars = new Handlebars();
        Template template = handlebars.compileInline("Hi {{name}}!");
        Person person = new Person();
        person.setName("Baeldung");

        String templateString = template.apply(person);

        assertThat(templateString).isEqualTo("Hi Baeldung!");
    }

    @Test
    public void whenMultipleParametersAreSupplied_ThenDisplays() throws IOException {
        Handlebars handlebars = new Handlebars();
        Template template = handlebars.compileInline("Hi {{name}}! This is {{topic}}.");
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("name", "Baeldung");
        parameterMap.put("topic", "Handlebars");

        String templateString = template.apply(parameterMap);

        assertThat(templateString).isEqualTo("Hi Baeldung! This is Handlebars.");
    }

    @Test
    public void whenNoLoaderIsGiven_ThenSearchesClasspath() throws IOException {
        Handlebars handlebars = new Handlebars();
        Template template = handlebars.compile("greeting");
        Person person = getPerson("Baeldung");

        String templateString = template.apply(person);

        assertThat(templateString).isEqualTo("Hi Baeldung!");
    }

    @Test
    public void whenClasspathTemplateLoaderIsGiven_ThenSearchesClasspathWithPrefixSuffix() throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader("/handlebars", ".html");
        Handlebars handlebars = new Handlebars(loader);
        Template template = handlebars.compile("greeting");
        Person person = getPerson("Baeldung");

        String templateString = template.apply(person);

        assertThat(templateString).isEqualTo("Hi Baeldung!");
    }

    @Test
    public void whenMultipleLoadersAreGiven_ThenSearchesSequentially() throws IOException {
        TemplateLoader firstLoader = new ClassPathTemplateLoader("/handlebars", ".html");
        TemplateLoader secondLoader = new ClassPathTemplateLoader("/templates", ".html");
        Handlebars handlebars = new Handlebars().with(firstLoader, secondLoader);
        Template template = handlebars.compile("greeting");
        Person person = getPerson("Baeldung");

        String templateString = template.apply(person);

        assertThat(templateString).isEqualTo("Hi Baeldung!");
    }

    private Person getPerson(String name) {
        Person person = new Person();
        person.setName(name);
        return person;
    }
}
