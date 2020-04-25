package com.baeldung.optional;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.*;

public class OptionalUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(OptionalUnitTest.class);

    // creating Optional
    @Test
    public void whenCreatesEmptyOptional_thenCorrect() {
        Optional<String> empty = Optional.empty();
        assertFalse(empty.isPresent());
    }

    @Test
    public void givenNonNull_whenCreatesNonNullable_thenCorrect() {
        String name = "baeldung";
        Optional<String> opt = Optional.of(name);
        assertTrue(opt.isPresent());
    }

    @Test(expected = NullPointerException.class)
    public void givenNull_whenThrowsErrorOnCreate_thenCorrect() {
        String name = null;
        Optional.of(name);
    }

    @Test
    public void givenNonNull_whenCreatesOptional_thenCorrect() {
        String name = "baeldung";
        Optional<String> opt = Optional.of(name);
        assertTrue(opt.isPresent());
    }

    @Test
    public void givenNonNull_whenCreatesNullable_thenCorrect() {
        String name = "baeldung";
        Optional<String> opt = Optional.ofNullable(name);
        assertTrue(opt.isPresent());
    }

    @Test
    public void givenNull_whenCreatesNullable_thenCorrect() {
        String name = null;
        Optional<String> opt = Optional.ofNullable(name);
        assertFalse(opt.isPresent());
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
        opt.ifPresent(name -> LOG.debug("{}", name.length()));
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
    
    @Test
    public void givenAnEmptyOptional_thenIsEmptyBehavesAsExpected() {
        Optional<String> opt = Optional.of("Baeldung");
        assertTrue(opt.isPresent());
     
        opt = Optional.ofNullable(null);
        assertFalse(opt.isPresent());
    }

    // Conditional Return With filter()
    @Test
    public void whenOptionalFilterWorks_thenCorrect() {
        Integer year = 2016;
        Optional<Integer> yearOptional = Optional.of(year);
        boolean is2016 = yearOptional.filter(y -> y == 2016)
            .isPresent();
        assertTrue(is2016);
        boolean is2017 = yearOptional.filter(y -> y == 2017)
            .isPresent();
        assertFalse(is2017);
    }

    @Test
    public void whenFiltersWithoutOptional_thenCorrect() {
        assertTrue(priceIsInRange1(new Modem(10.0)));
        assertFalse(priceIsInRange1(new Modem(9.9)));
        assertFalse(priceIsInRange1(new Modem(null)));
        assertFalse(priceIsInRange1(new Modem(15.5)));
        assertFalse(priceIsInRange1(null));

    }

    @Test
    public void whenFiltersWithOptional_thenCorrect() {
        assertTrue(priceIsInRange2(new Modem(10.0)));
        assertFalse(priceIsInRange2(new Modem(9.9)));
        assertFalse(priceIsInRange2(new Modem(null)));
        assertFalse(priceIsInRange2(new Modem(15.5)));
        assertFalse(priceIsInRange1(null));
    }

    public boolean priceIsInRange1(Modem modem) {
        boolean isInRange = false;
        if (modem != null && modem.getPrice() != null && (modem.getPrice() >= 10 && modem.getPrice() <= 15)) {
            isInRange = true;
        }
        return isInRange;
    }

    public boolean priceIsInRange2(Modem modem2) {
        return Optional.ofNullable(modem2)
            .map(Modem::getPrice)
            .filter(p -> p >= 10)
            .filter(p -> p <= 15)
            .isPresent();
    }

    // Transforming Value With map()
    @Test
    public void givenOptional_whenMapWorks_thenCorrect() {
        List<String> companyNames = Arrays.asList("paypal", "oracle", "", "microsoft", "", "apple");
        Optional<List<String>> listOptional = Optional.of(companyNames);

        int size = listOptional.map(List::size)
            .orElse(0);
        assertEquals(6, size);
    }

    @Test
    public void givenOptional_whenMapWorks_thenCorrect2() {
        String name = "baeldung";
        Optional<String> nameOptional = Optional.of(name);

        int len = nameOptional.map(String::length)
            .orElse(0);
        assertEquals(8, len);
    }

    @Test
    public void givenOptional_whenMapWorksWithFilter_thenCorrect() {
        String password = " password ";
        Optional<String> passOpt = Optional.of(password);
        boolean correctPassword = passOpt.filter(pass -> pass.equals("password"))
            .isPresent();
        assertFalse(correctPassword);

        correctPassword = passOpt.map(String::trim)
            .filter(pass -> pass.equals("password"))
            .isPresent();
        assertTrue(correctPassword);
    }

    // Transforming Value With flatMap()
    @Test
    public void givenOptional_whenFlatMapWorks_thenCorrect2() {
        Person person = new Person("john", 26);
        Optional<Person> personOptional = Optional.of(person);

        Optional<Optional<String>> nameOptionalWrapper = personOptional.map(Person::getName);
        Optional<String> nameOptional = nameOptionalWrapper.orElseThrow(IllegalArgumentException::new);
        String name1 = nameOptional.orElseThrow(IllegalArgumentException::new);
        assertEquals("john", name1);

        String name = personOptional.flatMap(Person::getName)
            .orElseThrow(IllegalArgumentException::new);
        assertEquals("john", name);
    }

    @Test
    public void givenOptional_whenFlatMapWorksWithFilter_thenCorrect() {
        Person person = new Person("john", 26);
        person.setPassword("password");
        Optional<Person> personOptional = Optional.of(person);

        String password = personOptional.flatMap(Person::getPassword)
            .filter(cleanPass -> cleanPass.equals("password"))
            .orElseThrow(IllegalArgumentException::new);
        assertEquals("password", password);
    }

    // Default Value With orElse
    @Test
    public void whenOrElseWorks_thenCorrect() {
        String nullName = null;
        String name = Optional.ofNullable(nullName)
            .orElse("john");
        assertEquals("john", name);
    }

    // Default Value With orElseGet
    @Test
    public void whenOrElseGetWorks_thenCorrect() {
        String nullName = null;
        String name = Optional.ofNullable(nullName)
            .orElseGet(() -> "john");
        assertEquals("john", name);

    }

    @Test
    public void whenOrElseGetAndOrElseOverlap_thenCorrect() {
        String text = null;
        LOG.debug("Using orElseGet:");
        String defaultText = Optional.ofNullable(text)
            .orElseGet(this::getMyDefault);
        assertEquals("Default Value", defaultText);

        LOG.debug("Using orElse:");
        defaultText = Optional.ofNullable(text)
            .orElse(getMyDefault());
        assertEquals("Default Value", defaultText);
    }

    @Test
    public void whenOrElseGetAndOrElseDiffer_thenCorrect() {
        String text = "Text present";
        LOG.debug("Using orElseGet:");
        String defaultText = Optional.ofNullable(text)
            .orElseGet(this::getMyDefault);
        assertEquals("Text present", defaultText);

        LOG.debug("Using orElse:");
        defaultText = Optional.ofNullable(text)
            .orElse(getMyDefault());
        assertEquals("Text present", defaultText);
    }

    // Exceptions With orElseThrow
    @Test(expected = IllegalArgumentException.class)
    public void whenOrElseThrowWorks_thenCorrect() {
        String nullName = null;
        String name = Optional.ofNullable(nullName)
            .orElseThrow(IllegalArgumentException::new);
    }

    public String getMyDefault() {
        LOG.debug("Getting default value...");
        return "Default Value";
    }
    
//    Uncomment code when code base is compatible with Java 11
//    @Test
//    public void givenAnEmptyOptional_thenIsEmptyBehavesAsExpected() {
//        Optional<String> opt = Optional.of("Baeldung");
//        assertFalse(opt.isEmpty());
//     
//        opt = Optional.ofNullable(null);
//        assertTrue(opt.isEmpty());
//    }

}