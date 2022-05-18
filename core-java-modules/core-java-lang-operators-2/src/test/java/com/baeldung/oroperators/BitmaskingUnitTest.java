package com.baeldung.oroperators;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class BitmaskingUnitTest {

    @Test
    public void givenIntegerShouldPrintBinaryRepresentation() {
        // given
        int intRepresentation = 1094795523;

        // expected
        String stringRepresentation = getStringRepresentation(intRepresentation);
        assertEquals(stringRepresentation, "01000001010000010100000100000011");
    }

    private String getStringRepresentation(int intRepresentation) {
        String binaryString = Integer.toBinaryString(intRepresentation);
        return padWithZeros(binaryString);
    }

    private String padWithZeros(String binaryString) {
        return String.format("%" + 32 + "s", binaryString).replace(' ', '0');
    }

    @Test
    public void givenBinaryRepresentationShouldReturnNumber() {
        // given
        String stringRepresentation = "01000001010000010100000100000011";

        // expected
        int intRepresentation = Integer.parseUnsignedInt(stringRepresentation, 2);
        assertEquals(intRepresentation, 1094795523);
    }

    @Test
    public void givenBinaryRepresentationShouldReturnCharacter() {
        // given
        String stringRepresentation = "010000010100000101000001";

        // expected
        assertEquals(binaryToText(stringRepresentation), "AAA");
    }

    @Test
    public void givenIntAndPositionNumberShouldReturnBitValue() {
        // given
        String stringRepresentation = "010000010100000101000001000000011";
        int intRepresentation = Integer.parseUnsignedInt(stringRepresentation, 2);

        // when
        boolean value1 = extractValueAtPosition(intRepresentation, 1);
        boolean value2 = extractValueAtPosition(intRepresentation, 2);
        boolean value3 = extractValueAtPosition(intRepresentation, 3);

        // then
        assertTrue(value1);
        assertTrue(value2);
        assertFalse(value3);

    }

    @Test
    public void givenIntegerShouldExtractLastThreeBytes() {
        // given
        int intRepresentation = 1094795523;

        // when
        int lastThreeBites = intRepresentation >> 8;

        // expected
        String stringRepresentation = getStringRepresentation(lastThreeBites);
        assertEquals(stringRepresentation, "00000000010000010100000101000001");
        assertEquals(binaryToText(stringRepresentation), "AAA");
    }

    @Test
    public void givenIntegerShouldApplyMask() {
        // given
        int intRepresentation = Integer.parseUnsignedInt("00000000010000010100000101000001", 2);
        int mask = Integer.parseUnsignedInt("00000000000000000000000000000011", 2);
        int mask2 = Integer.parseUnsignedInt("00000000000000000000000000000001", 2);

        // when
        int masked = intRepresentation & mask;
        int masked2 = intRepresentation & mask2;

        // expected
        assertEquals(getStringRepresentation(masked), "00000000000000000000000000000001");
        assertEquals(getStringRepresentation(masked2), "00000000000000000000000000000001");
        assertNotEquals(masked, mask);
        assertEquals(masked2, mask2);
        assertFalse((intRepresentation & mask) == mask);
    }

    private boolean extractValueAtPosition(int intRepresentation, int position) {
        String mask = getStringRepresentation(1 << (position - 1));
        System.out.println(getStringRepresentation(intRepresentation));
        System.out.println(mask);
        System.out.println("-------------------------------- &");
        System.out.println(getStringRepresentation((intRepresentation) & (1 << (position - 1))));
        System.out.println();
        return ((intRepresentation) & (1 << (position - 1))) != 0;
    }

    private static String binaryToText(String stringRepresentation) {
        return Arrays.stream(stringRepresentation.split("(?<=\\G.{8})"))
                .filter(eightBits -> !eightBits.equals("00000000"))
                .map(eightBits -> (char)Integer.parseInt(eightBits, 2))
                .collect(
                        StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append
                ).toString();
    }
}
