package com.baeldung.stringtolong;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.common.primitives.Longs;

public class StringToLongPrimitiveOrLongObjectUnitTest {

    @Test
    void givenString_whenUsingLongConstructor_thenObtainLongObject() {
        Long l = Long.valueOf("2147483648");
        assertThat(l).isEqualTo(2147483648L);
    }

    @Test
    void givenInvalidString_whenUsingLongConstructor_thenNumberFormatExceptionThrown() {
        Assertions.assertThrows(NumberFormatException.class, () -> Long.valueOf("Invalid String"));
    }

    @Test
    void givenString_whenUsingLongValueOf_thenObtainLongObject() {
        Long l = Long.valueOf("2147483648");
        assertThat(l).isEqualTo(2147483648L);
    }

    @Test
    void givenInvalidString_whenUsingLongValueOf_thenNumberFormatExceptionThrown() {
        Assertions.assertThrows(NumberFormatException.class, () -> Long.valueOf("Invalid String"));
    }

    @Test
    void givenString_whenUsingParseLong_thenObtainLongPrimitive() {
        long l = Long.parseLong("2147483648");
        assertThat(l).isEqualTo(2147483648L);
    }

    @Test
    void givenInvalidString_whenUsingParseLong_thenNumberFormatExceptionThrown() {
        Assertions.assertThrows(NumberFormatException.class, () -> Long.parseLong("Invalid String"));
    }

    @Test
    void givenHexadecimalString_whenUsingLongDecode_thenObtainLongObject() {
        Long l = Long.decode("0x80000000");
        assertThat(l).isEqualTo(2147483648L);
    }

    @Test
    void givenInvalidString_whenUsingLongDecode_thenNumberFormatExceptionThrown() {
        Assertions.assertThrows(NumberFormatException.class, () -> Long.decode("Invalid String"));
    }

    @Test
    void givenHexadecimalString_whenUsingApacheCommonsNumberUtils_thenObtainLongObject() {
        Long l = NumberUtils.createLong("0x80000000");
        assertThat(l).isEqualTo(2147483648L);
    }

    @Test
    void givenInvalidString_whenUsingApacheCommonsNumberUtils_thenNumberFormatExceptionThrown() {
        Assertions.assertThrows(NumberFormatException.class, () -> NumberUtils.createLong("Invalid String"));
    }

    @Test
    void givenString_whenUsingParseUnsignedLong_thenObtainUnsignedLongObject() {
        Long l = Long.parseUnsignedLong("9223372036854775808");
        assertThat(Long.toUnsignedString(l)).isEqualTo("9223372036854775808");
    }

    @Test
    void givenInvalidString_whenUsingParseUnsignedLong_thenNumberFormatExceptionThrown() {
        Assertions.assertThrows(NumberFormatException.class, () -> Long.parseUnsignedLong("Invalid String"));
    }

    @Test
    void givenString_whenUsingGuavaLongs_thenObtainLongObject() {
        Long l = Longs.tryParse("2147483648");
        assertThat(l).isEqualTo(2147483648L);
    }

    @Test
    void givenInvalidString_whenUsingGuavaLongs_thenObtainNull() {
        assertThat(Longs.tryParse("Invalid String")).isNull();
    }
}