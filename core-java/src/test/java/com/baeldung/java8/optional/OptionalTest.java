package com.baeldung.java8.optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import com.baeldung.java_8_features.Person;

import org.junit.Test;

public class OptionalTest {
    // creating Optional
    @Test
    public void whenCreatesEmptyOptional_thenCorrect() {
        Optional<String> empty = Optional.empty();
        assertFalse(empty.isPresent());
    }

    @Test
    public void givenNonNull_whenCreatesNonNullable_thenCorrect() {
        String name = "baeldung";
        Optional.of(name);
    }

    @Test(expected = NullPointerException.class)
    public void givenNull_whenThrowsErrorOnCreate_thenCorrect() {
        String name = null;
        Optional<String> opt = Optional.of(name);
    }

    @Test
    public void givenNonNull_whenCreatesOptional_thenCorrect() {
        String name = "baeldung";
        Optional<String> opt = Optional.of(name);
        assertEquals("Optional[baeldung]", opt.toString());
    }

    @Test
    public void givenNonNull_whenCreatesNullable_thenCorrect() {
        String name = "baeldung";
        Optional<String> opt = Optional.ofNullable(name);
        assertEquals("Optional[baeldung]", opt.toString());
    }

    @Test
    public void givenNull_whenCreatesNullable_thenCorrect() {
        String name = null;
        Optional<String> opt = Optional.ofNullable(name);
        assertEquals("Optional.empty", opt.toString());
    }
    // Checking Value With isPresent()

    @Test
    public void givenOptional_whenIsPresentWorks_thenCorrect() {
        Optional<String> opt = Optional.of("Baeldung");
        assertTrue(opt.isPresent());

        opt = Optional.ofNullable(null);
        assertFalse(opt.isPresent());
    }

    // Condition Action With ifPresent()
    @Test
    public void givenOptional_whenIfPresentWorks_thenCorrect() {
        Optional<String> opt = Optional.of("baeldung");
        opt.ifPresent(name -> System.out.println(name.length()));
        opt.ifPresent(String::length);
    }

    // returning Value With get()
    @Test
    public void givenOptional_whenGetsValue_thenCorrect() {
        Optional<String> opt = Optional.of("baeldung");
        String name = opt.get();
        assertEquals("baeldung", name);
    }

    @Test(expected = NoSuchElementException.class)
    public void givenOptionalWithNull_whenGetThrowsException_thenCorrect() {
        Optional<String> opt = Optional.ofNullable(null);
        String name = opt.get();
    }

    // Conditional Return With filter()
    @Test
    public void whenOptionalFilterWorks_thenCorrect() {
        Integer year = 2016;
        Optional<Integer> yearOptional = Optional.of(year);
        boolean is2016 = yearOptional.filter(y -> y == 2016).isPresent();
        assertTrue(is2016);
        boolean is2017 = yearOptional.filter(y -> y == 2017).isPresent();
        assertFalse(is2017);
    }

    // Transforming Value With map()
    @Test
    public void givenOptional_whenMapWorks_thenCorrect() {
        List<String> companyNames = Arrays.asList("paypal", "oracle", "", "microsoft", "", "apple");
        Optional<List<String>> listOptional = Optional.of(companyNames);

        int size = listOptional.map(list -> list.size()).get();
        assertEquals(6, size);
    }

    @Test
    public void givenOptional_whenMapWorks_thenCorrect2() {
        String name = "baeldung";
        Optional<String> nameOptional = Optional.of(name);

        int len = nameOptional.map(s -> s.length()).get();
        assertEquals(8, len);
    }

    @Test
    public void givenOptional_whenMapWorksWithFilter_thenCorrect() {
        String password = " password ";
        Optional<String> passOpt = Optional.of(password);
        boolean correctPassword = passOpt.filter(pass -> pass.equals("password")).isPresent();
        assertFalse(correctPassword);

        correctPassword = passOpt.map(pass -> pass.trim()).filter(pass -> pass.equals("password")).isPresent();
        assertTrue(correctPassword);
    }

    // Transforming Value With flatMap()
    @Test
    public void givenOptional_whenFlatMapWorks_thenCorrect2() {
        Person person = new Person("john", 26);
        Optional<Person> personOptional = Optional.of(person);

        Optional<Optional<String>> nameOptionalWrapper = personOptional.map(p -> p.getName());
        Optional<String> nameOptional = nameOptionalWrapper.get();
        String name1 = nameOptional.get();
        assertEquals("john", name1);

        String name = personOptional.flatMap(p -> p.getName()).get();
        assertEquals("john", name);
    }

    @Test
    public void givenOptional_whenFlatMapWorksWithFilter_thenCorrect() {
        Person person = new Person("john", 26);
        person.setPassword("password");
        Optional<Person> personOptional = Optional.of(person);

        String password = personOptional.flatMap(p -> p.getPassword()).filter(cleanPass -> cleanPass.equals("password")).get();
        assertEquals("password", password);
    }

    // Default Value With orElse
    @Test
    public void whenOrElseWorks_thenCorrect() {
        String nullName = null;
        String name = Optional.ofNullable(nullName).orElse("john");
        assertEquals("john", name);
    }

    // Default Value With orElseGet
    @Test
    public void whenOrElseGetWorks_thenCorrect() {
        String nullName = null;
        String name = Optional.ofNullable(nullName).orElseGet(() -> "john");
        assertEquals("john", name);

        name = Optional.ofNullable(nullName).orElseGet(() -> {
            return "doe";
        });
        assertEquals("doe", name);

    }

    @Test
    public void whenOrElseGetAndOrElseOverlap_thenCorrect() {
        String text = null;
        System.out.println("Using orElseGet:");
        String defaultText = Optional.ofNullable(text).orElseGet(this::getMyDefault);
        assertEquals("Default Value", defaultText);

        System.out.println("Using orElse:");
        defaultText = Optional.ofNullable(text).orElse(getMyDefault());
        assertEquals("Default Value", defaultText);
    }

    @Test
    public void whenOrElseGetAndOrElseDiffer_thenCorrect() {
        String text = "Text present";
        System.out.println("Using orElseGet:");
        String defaultText = Optional.ofNullable(text).orElseGet(this::getMyDefault);
        assertEquals("Text present", defaultText);

        System.out.println("Using orElse:");
        defaultText = Optional.ofNullable(text).orElse(getMyDefault());
        assertEquals("Text present", defaultText);
    }

    // Exceptions With orElseThrow
    @Test(expected = IllegalArgumentException.class)
    public void whenOrElseThrowWorks_thenCorrect() {
        String nullName = null;
        String name = Optional.ofNullable(nullName).orElseThrow(IllegalArgumentException::new);
    }

    public String getMyDefault() {
        System.out.println("Getting default value...");
        return "Default Value";
    }
}
