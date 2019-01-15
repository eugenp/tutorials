package com.baeldung.annotations.parser;

import com.baeldung.annotations.Address;
import org.junit.Test;

public class ZipCodeParserTest {

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidZipCodeLength() {
        Address address = new Address("12345", "New York", "US");
        ZipCodeParser.validate(address);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidZipCodeLength2() {
        Address address = new Address("1234567", "London", "UK");
        ZipCodeParser.validate(address);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidZipCodeNotNumeric() {
        Address address = new Address("1234BA", "Los Angeles", "US");
        ZipCodeParser.validate(address);
    }

    @Test
    public void testValidZipCode() {
        Address address = new Address("123456", "New Delhi", "India");
        ZipCodeParser.validate(address);
    }
}
