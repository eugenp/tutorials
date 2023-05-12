package com.baeldung.algorithms.conversion;

import java.math.BigInteger;



import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import com.google.common.io.BaseEncoding;

import jakarta.xml.bind.DatatypeConverter;

public class HexStringConverter {

    /**
     * Create a byte Array from String of hexadecimal digits using Character conversion 
     * @param hexString - Hexadecimal digits as String
     * @return Desired byte Array
     */
    public byte[] decodeHexString(String hexString) {
        if (hexString.length() % 2 == 1) {
            throw new IllegalArgumentException("Invalid hexadecimal String supplied.");
        }
        byte[] bytes = new byte[hexString.length() / 2];

        for (int i = 0; i < hexString.length(); i += 2) {
            bytes[i / 2] = hexToByte(hexString.substring(i, i + 2));
        }
        return bytes;
    }

    /**
     * Create a String of hexadecimal digits from a byte Array using Character conversion
     * @param byteArray - The byte Array
     * @return Desired String of hexadecimal digits in lower case
     */
    public String encodeHexString(byte[] byteArray) {
        StringBuffer hexStringBuffer = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            hexStringBuffer.append(byteToHex(byteArray[i]));
        }
        return hexStringBuffer.toString();
    }

    public String byteToHex(byte num) {
        char[] hexDigits = new char[2];
        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
        hexDigits[1] = Character.forDigit((num & 0xF), 16);
        return new String(hexDigits);
    }

    public byte hexToByte(String hexString) {
        int firstDigit = toDigit(hexString.charAt(0));
        int secondDigit = toDigit(hexString.charAt(1));
        return (byte) ((firstDigit << 4) + secondDigit);
    }

    private int toDigit(char hexChar) {
        int digit = Character.digit(hexChar, 16);
        if(digit == -1) {
            throw new IllegalArgumentException("Invalid Hexadecimal Character: "+ hexChar);
        }
        return digit;
    }

    public String encodeUsingBigIntegerToString(byte[] bytes) {
        BigInteger bigInteger = new BigInteger(1, bytes);
        return bigInteger.toString(16);
    }

    public String encodeUsingBigIntegerStringFormat(byte[] bytes) {
        BigInteger bigInteger = new BigInteger(1, bytes);
        return String.format("%0" + (bytes.length << 1) + "x", bigInteger);
    }

    public byte[] decodeUsingBigInteger(String hexString) {
        byte[] byteArray = new BigInteger(hexString, 16).toByteArray();
        if (byteArray[0] == 0) {
            byte[] output = new byte[byteArray.length - 1];
            System.arraycopy(byteArray, 1, output, 0, output.length);
            return output;
        }
        return byteArray;
    }

    public String encodeUsingDataTypeConverter(byte[] bytes) {
        return DatatypeConverter.printHexBinary(bytes);
    }

    public byte[] decodeUsingDataTypeConverter(String hexString) {
        return DatatypeConverter.parseHexBinary(hexString);
    }

    public String encodeUsingApacheCommons(byte[] bytes) {
        return Hex.encodeHexString(bytes);
    }

    public byte[] decodeUsingApacheCommons(String hexString) throws DecoderException {
        return Hex.decodeHex(hexString);
    }

    public String encodeUsingGuava(byte[] bytes) {
        return BaseEncoding.base16()
            .encode(bytes);
    }

    public byte[] decodeUsingGuava(String hexString) {
        return BaseEncoding.base16()
            .decode(hexString.toUpperCase());
    }
}
