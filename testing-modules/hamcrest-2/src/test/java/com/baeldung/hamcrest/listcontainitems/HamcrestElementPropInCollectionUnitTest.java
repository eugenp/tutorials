package com.baeldung.hamcrest.listcontainitems;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class HamcrestElementPropInCollectionUnitTest {
    private static final List<Developer> DEVELOPERS = List.of(
        new Developer("Kai", 28, "Linux", List.of("Kotlin", "Python")),
        new Developer("Liam", 26, "MacOS", List.of("Java", "C#")),
        new Developer("Kevin", 24, "MacOS", List.of("Python", "Go")),
        new Developer("Saajan", 22, "MacOS", List.of("Ruby", "Php", "Typescript")),
        new Developer("Eric", 27, "Linux", List.of("Java", "C"))
    );

    @Test
    void whenUsingHamcrestMatchers_thenCorrect() {
        assertThat(DEVELOPERS, hasItem(hasProperty("os", equalTo("Linux"))));
        assertThat(DEVELOPERS, hasItem(hasProperty("name", is("Kai"))));
        assertThat(DEVELOPERS, hasItem(hasProperty("age", lessThan(28))));
        assertThat(DEVELOPERS, hasItem(hasProperty("languages", hasItem("Go"))));

        assertThat(DEVELOPERS, hasItem(
            anyOf(
                hasProperty("languages", hasItem("C")),
                hasProperty("os", is("Windows"))) // <-- No dev has the OS "Windows"
        ));

        assertThat(DEVELOPERS, hasItem(
            allOf(
                hasProperty("languages", hasItem("C")),
                hasProperty("os", is("Linux")),
                hasProperty("age", greaterThan(25)))
        ));

        assertThat(DEVELOPERS, not(hasItem( // <-- not() matcher
            allOf(
                hasProperty("languages", hasItem("C#")),
                hasProperty("os", is("Linux")),
                hasProperty("age", greaterThan(25)))
        )));
    }

    @Test
    void whenUsingStreamAnyMatch_thenCorrect() {
        assertTrue(DEVELOPERS.stream().anyMatch(dev -> dev.getOs().equals("Linux")));
        assertTrue(DEVELOPERS.stream().anyMatch(dev -> dev.getAge() < 28));
        assertTrue(DEVELOPERS.stream().anyMatch(dev -> dev.getLanguages().contains("Go")));
        assertTrue(DEVELOPERS.stream().anyMatch(dev -> dev.getLanguages().contains("C") && dev.getOs().equals("Linux")));
    }

}