package com.baeldung.javafaker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.LocaleDoesNotExistException;
import com.github.javafaker.service.RandomService;

public class JavaFakerUnitTest {

    private Faker faker;

    @Before
    public void setUp() throws Exception {
        faker = new Faker();
    }

    @Test
    public void givenJavaFaker_whenAddressObjectCalled_checkValidAddressInfoGiven() throws Exception {

        Faker faker = new Faker();

        String streetName = faker.address()
            .streetName();
        String number = faker.address()
            .buildingNumber();
        String city = faker.address()
            .city();
        String country = faker.address()
            .country();

        System.out.println(String.format("%s\n%s\n%s\n%s", number, streetName, city, country));

    }

    @Test
    public void givenJavaFakersWithSameSeed_whenNameCalled_CheckSameName() throws Exception {

        Faker faker1 = new Faker(new Random(24));
        Faker faker2 = new Faker(new Random(24));

        assertEquals(faker1.name()
            .firstName(),
            faker2.name()
                .firstName());
    }

    @Test
    public void givenJavaFakersWithDifferentLocals_checkZipCodesMatchRegex() throws Exception {

        Faker ukFaker = new Faker(new Locale("en-GB"));
        Faker usFaker = new Faker(new Locale("en-US"));

        System.out.println(String.format("American zipcode: %s", usFaker.address()
            .zipCode()));
        System.out.println(String.format("British postcode: %s", ukFaker.address()
            .zipCode()));

        Pattern ukPattern = Pattern.compile("([Gg][Ii][Rr] 0[Aa]{2})|((([A-Za-z][0-9]{1,2})|(([A-Za-z][A-Ha-hJ-Yj-y][0-9]{1,2})|(([A-Za-z][0-9][A-Za-z])|([A-Za-z][A-Ha-hJ-Yj-y][0-9]?[A-Za-z]))))\\s?[0-9][A-Za-z]{2})");
        Matcher ukMatcher = ukPattern.matcher(ukFaker.address()
            .zipCode());

        assertTrue(ukMatcher.find());

        Matcher usMatcher = Pattern.compile("^\\d{5}(?:[-\\s]\\d{4})?$")
            .matcher(usFaker.address()
                .zipCode());

        assertTrue(usMatcher.find());

    }

    @Test
    public void givenJavaFakerService_testFakersCreated() throws Exception {

        RandomService randomService = new RandomService();

        System.out.println(randomService.nextBoolean());
        System.out.println(randomService.nextDouble());

        Faker faker = new Faker(new Random(randomService.nextLong()));

        System.out.println(faker.address()
            .city());

    }

    @Test
    public void testFakeValuesService() throws Exception {

        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-GB"), new RandomService());

        String email = fakeValuesService.bothify("????##@gmail.com");
        Matcher emailMatcher = Pattern.compile("\\w{4}\\d{2}@gmail.com")
            .matcher(email);
        assertTrue(emailMatcher.find());

        String alphaNumericString = fakeValuesService.regexify("[a-z1-9]{10}");
        Matcher alphaNumericMatcher = Pattern.compile("[a-z1-9]{10}")
            .matcher(alphaNumericString);
        assertTrue(alphaNumericMatcher.find());

    }

    @Test(expected = LocaleDoesNotExistException.class)
    public void givenWrongLocale_whenFakerIsInitialised_testLocaleDoesNotExistExceptionIsThrown() throws Exception {

        Faker wrongLocaleFaker = new Faker(new Locale("en-seaWorld"));

    }
}