package com.baeldung.stringtolong;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.common.primitives.Longs;

public class StringToLongPrimitiveOrLongObjectUnitTest {

    @Test
    public void givenString_whenUsingLongConstructor_thenObtainLongObject() {
        Long l = new Long("123456");
        assertThat(l).isEqualTo(123456L);
    }

    @Test
    public void givenInvalidString_whenUsingLongConstructor_thenNumberFormatExceptionThrown() {
        Assertions.assertThrows(NumberFormatException.class, () -> new Long("Invalid String"));
    }

    @Test
    public void givenString_whenUsingLongValueOf_thenObtainLongObject() {
        Long l = Long.valueOf("123456");
        assertThat(l).isEqualTo(123456L);
    }

    @Test
    public void givenInvalidString_whenUsingLongValueOf_thenNumberFormatExceptionThrown() {
        Assertions.assertThrows(NumberFormatException.class, () -> Long.valueOf("Invalid String"));
    }

    @Test
    public void givenString_whenUsingParseLong_thenObtainLongPrimitive() {
        long l = Long.parseLong("123456");
        assertThat(l).isEqualTo(123456L);
    }

    @Test
    public void givenInvalidString_whenUsingParseLong_thenNumberFormatExceptionThrown() {
        Assertions.assertThrows(NumberFormatException.class, () -> Long.parseLong("Invalid String"));
    }

    @Test
    public void givenHexadecimalString_whenUsingLongDecode_thenObtainLongObject() {
        Long l = Long.decode("0x1e240");
        assertThat(l).isEqualTo(123456L);
    }

    @Test
    public void givenInvalidString_whenUsingLongDecode_thenNumberFormatExceptionThrown() {
        Assertions.assertThrows(NumberFormatException.class, () -> Long.decode("Invalid String"));
    }

    @Test
    public void givenHexadecimalString_whenUsingApacheCommons_thenObtainLongObject() {
        Long l = NumberUtils.createLong("0x1e240");
        assertThat(l).isEqualTo(123456L);
    }

    @Test
    public void givenInvalidString_whenUsingApacheCommons_thenNumberFormatExceptionThrown() {
        Assertions.assertThrows(NumberFormatException.class, () -> NumberUtils.createLong("Invalid String"));
    }

    @Test
    public void givenString_whenUsingGuava_thenObtainLongObject() {
        Long l = Longs.tryParse("123456");
        assertThat(l).isEqualTo(123456L);
    }

    @Test
    public void givenInvalidString_whenUsingGuava_thenObtainNull() {
        assertThat(Longs.tryParse("Invalid String")).isNull();
    }

    @Test
    public void givenString_whenUsingParseUnsignedLong_thenObtainUnsignedLongObject() {
        Long l = Long.parseUnsignedLong("9223372036854775808");
        assertThat(Long.toUnsignedString(l)).isEqualTo("9223372036854775808");
    }

    @Test
    public void givenInvalidString_whenUsingParseUnsignedLong_thenNumberFormatExceptionThrown() {
        Assertions.assertThrows(NumberFormatException.class, () -> Long.parseUnsignedLong("Invalid String"));
    }
}