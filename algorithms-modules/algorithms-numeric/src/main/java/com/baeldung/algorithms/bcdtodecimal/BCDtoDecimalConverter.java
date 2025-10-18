package com.baeldung.algorithms.bcdtodecimal;

public class BCDtoDecimalConverter {
    /**
     * Converts a single packed BCD byte to an integer.
     * Each byte represents two decimal digits.
     *
     * @param bcdByte The BCD byte to convert.
     * @return The decimal integer value.
     * @throws IllegalArgumentException if any nibble contains a non-BCD value (>9).
     */
    public static int convertPackedByte(byte bcdByte) {
        int resultDecimal;
        int upperNibble = (bcdByte >> 4) & 0x0F;
        int lowerNibble = bcdByte & 0x0F;
        if (upperNibble > 9 || lowerNibble > 9) {
            throw new IllegalArgumentException(
                String.format("Invalid BCD format: byte 0x%02X contains non-decimal digit.", bcdByte)
            );
        }
        resultDecimal = upperNibble * 10 + lowerNibble;
        return resultDecimal;
    }

    /**
     * Converts a BCD byte array to a long decimal value.
     * Each byte in the array iis mapped to a packed BCD byte,
     * representing two BCD nibbles.
     *
     * @param bcdArray The array of BCD bytes.
     * @return The combined long decimal value.
     * @throws IllegalArgumentException if any nibble contains a non-BCD value (>9).
     */
    public static long convertPackedByteArray(byte[] bcdArray) {
        long resultDecimal = 0;
        for (byte bcd : bcdArray) {
            int upperNibble = (bcd >> 4) & 0x0F;
            int lowerNibble = bcd & 0x0F;

            if (upperNibble > 9 || lowerNibble > 9) {
                throw new IllegalArgumentException("Invalid BCD format: nibble contains non-decimal digit.");
            }

            resultDecimal = resultDecimal * 100 + (upperNibble * 10 + lowerNibble);
        }
        return resultDecimal;
    }
}