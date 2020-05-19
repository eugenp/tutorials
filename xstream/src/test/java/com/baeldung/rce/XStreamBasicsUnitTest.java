package com.baeldung.rce;

import com.thoughtworks.xstream.XStream;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Demonstrates XStream basics
 */
public final class XStreamBasicsUnitTest {

    private XStream xstream;

    @Before
    public void before() {
        xstream = new XStream();
        xstream.alias("person", Person.class);
    }

    @Test
    public void whenWritePerson_thenWritesExpectedXml() {
        Person person = new Person();
        person.setFirst("John");
        person.setLast("Smith");

        String xml = xstream.toXML(person);

        // @formatter:off
        String expected = ""
            + "<person>\n"
            + "  <first>John</first>\n"
            + "  <last>Smith</last>\n"
            + "</person>";
        // @formatter:on
        assertEquals(expected, xml);

    }

    @Test
    public void whenReadXmlAsPerson_thenReturnsNewPerson() {
        // @formatter:off
        String xml = ""
            + "<person>"
            + "  <first>John</first>"
            + "  <last>Smith</last>"
            + "</person>";
        // @formatter:on

        Person person = (Person) xstream.fromXML(xml);

        Person expected = new Person();
        expected.setFirst("John");
        expected.setLast("Smith");
        assertEquals(person, expected);
    }

    @Test
    public void givenXmlRepresentationOfMap_whenDeserialize_thenBuildsMap() {
        // @formatter:off
        String xml = ""
            + "<map>"
            + "  <element>"
            + "    <string>foo</string>"
            + "    <int>10</int>"
            + "  </element>"
            + "</map>";
        // @formatter:on
        @SuppressWarnings("unchecked")
        Map<String, Integer> actual = (Map<String, Integer>) xstream.fromXML(xml);

        final Map<String, Integer> expected = Collections.singletonMap("foo", 10);

        assertEquals(expected, actual);
    }

}
