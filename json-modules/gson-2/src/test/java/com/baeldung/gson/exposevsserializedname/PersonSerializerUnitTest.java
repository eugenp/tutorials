package com.baeldung.gson.exposevsserializedname;

import com.baeldung.gson.entities.BankAccount;
import com.baeldung.gson.entities.Country;
import com.baeldung.gson.entities.Person;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PersonSerializerUnitTest {
    private static final Logger logger = LoggerFactory.getLogger(PersonSerializerUnitTest.class);
    private Person person = null;
    private Country country = null;
    @Before
    public void preparePersonObj() {
        List<BankAccount> accounts = new ArrayList<>();
        BankAccount acc1 = new BankAccount("4565432312", "Bank of America");
        BankAccount acc2 = new BankAccount("4565432616", "Bank of America");
        accounts.add(acc1);
        accounts.add(acc2);
        person = new Person(
                "James", "Cameron", "james.cameron@gmail.com",
                "secret", accounts);

        country = new Country("India", "New Delhi", "Asia");
    }

    @Test
    public void whenUseCustomGson_thenDonotSerializeAccountNumAndPassword () {

        String personJson = PersonSerializer.serializeWithConfiguredGson(person);
        logger.info(personJson);
        assertFalse("Test failed: password found", personJson.contains("password"));
        assertFalse("Test failed: account number found", personJson.contains("accountNumber:"));
    }

    @Test
    public void whenUseDefaultGson_thenSerializeAccountNumAndPassword () {

        String personJson = PersonSerializer.serializeWithDefaultGson(person);
        logger.info(personJson);
        assertTrue("Test failed: password not found", personJson.contains("password"));
        assertTrue("Test failed: account number not found", personJson.contains("accountNumber"));
    }

    @Test
    public void whenUseSerializedAnnotation_thenUseSerializedNameinJsonString() {
        String countryJson = PersonSerializer.toJsonString(country);
        logger.info(countryJson);
        assertFalse("Test failed: No change in the keys", countryJson.contains("countryName"));
        assertFalse("Test failed: No change in the keys", countryJson.contains("contentName"));
        assertFalse("Test failed: No change in the keys", countryJson.contains("countryCapital"));

        assertTrue("Test failed: No change in the keys", countryJson.contains("name"));
        assertTrue("Test failed: No change in the keys", countryJson.contains("continent"));
        assertTrue("Test failed: No change in the keys", countryJson.contains("capital"));
    }

    @Test
    public void whenJsonStrCreatedWithCustomKeys_thenCreateObjUsingGson() {
        String countryJson = PersonSerializer.toJsonString(country);
        Country country = PersonSerializer.fromJsonString(countryJson);
        logger.info(country.toString());
        assertEquals("Fail: Object creation failed", country.getCountryName(), "India");
        assertEquals("Fail: Object creation failed", country.getCountryCapital(), "New Delhi");
        assertEquals("Fail: Object creation failed", country.getContinentName(), "Asia");
    }
}
