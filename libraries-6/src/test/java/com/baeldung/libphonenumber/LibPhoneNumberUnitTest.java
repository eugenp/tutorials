package com.baeldung.libphonenumber;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber.CountryCodeSource;

public class LibPhoneNumberUnitTest {

    private static final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    @Test
    public void givenPhoneNumber_whenValid_thenOK() throws Exception {

        PhoneNumber phone = phoneNumberUtil.parse("+911234567890", CountryCodeSource.UNSPECIFIED.name());

        assertTrue(phoneNumberUtil.isValidNumber(phone));
        assertTrue(phoneNumberUtil.isValidNumberForRegion(phone, "IN"));
        assertFalse(phoneNumberUtil.isValidNumberForRegion(phone, "US"));
        assertTrue(phoneNumberUtil.isValidNumber(phoneNumberUtil.getExampleNumber("IN")));
    }

    @Test
    public void givenPhoneNumber_whenAlphaNumber_thenValid() {
        assertTrue(phoneNumberUtil.isAlphaNumber("325-CARS"));
        assertTrue(phoneNumberUtil.isAlphaNumber("0800 REPAIR"));
        assertTrue(phoneNumberUtil.isAlphaNumber("1-800-MY-APPLE"));
        assertTrue(phoneNumberUtil.isAlphaNumber("1-800-MY-APPLE.."));
        assertFalse(phoneNumberUtil.isAlphaNumber("+876 1234-1234"));
    }

    @Test
    public void givenPhoneNumber_whenPossibleForType_thenValid() {
        PhoneNumber number = new PhoneNumber();
        number.setCountryCode(54);

        number.setNationalNumber(123456);
        assertTrue(phoneNumberUtil.isPossibleNumberForType(number, PhoneNumberType.FIXED_LINE));
        assertFalse(phoneNumberUtil.isPossibleNumberForType(number, PhoneNumberType.TOLL_FREE));

        number.setNationalNumber(12345678901L);
        assertFalse(phoneNumberUtil.isPossibleNumberForType(number, PhoneNumberType.FIXED_LINE));
        assertTrue(phoneNumberUtil.isPossibleNumberForType(number, PhoneNumberType.MOBILE));
        assertFalse(phoneNumberUtil.isPossibleNumberForType(number, PhoneNumberType.TOLL_FREE));
    }

    @Test
    public void givenPhoneNumber_whenPossible_thenValid() {
        PhoneNumber number = new PhoneNumber();
        number.setCountryCode(1)
            .setNationalNumber(123000L);
        assertFalse(phoneNumberUtil.isPossibleNumber(number));
        assertFalse(phoneNumberUtil.isPossibleNumber("+1 343 253 00000", "US"));
        assertFalse(phoneNumberUtil.isPossibleNumber("(343) 253-00000", "US"));
        assertFalse(phoneNumberUtil.isPossibleNumber("dial p for pizza", "US"));
        assertFalse(phoneNumberUtil.isPossibleNumber("123-000", "US"));
    }

    @Test
    public void givenPhoneNumber_whenNumberGeographical_thenValid() throws NumberParseException {

        PhoneNumber phone = phoneNumberUtil.parse("+911234567890", "IN");
        assertTrue(phoneNumberUtil.isNumberGeographical(phone));

        phone = new PhoneNumber().setCountryCode(1)
            .setNationalNumber(2530000L);
        assertFalse(phoneNumberUtil.isNumberGeographical(phone));

        phone = new PhoneNumber().setCountryCode(800)
            .setNationalNumber(12345678L);
        assertFalse(phoneNumberUtil.isNumberGeographical(phone));
    }
}
