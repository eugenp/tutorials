package com.baeldung.algorithms.conversion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.hamcrest.text.IsEqualIgnoringCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.algorithms.conversion.HexStringConverter;

class ByteArrayConverterUnitTest {

    private HexStringConverter hexStringConverter;

    @BeforeEach
    public void setup() {
        hexStringConverter = new HexStringConverter();
    }

    @Test
    void shouldEncodeByteArrayToHexStringUsingBigIntegerToString() {
        byte[] bytes = getSampleBytes();
        String hexString = getSampleHexString();
        if(hexString.charAt(0) == '0') {
            hexString = hexString.substring(1);
        }
        String output = hexStringConverter.encodeUsingBigIntegerToString(bytes);
        assertThat(output, IsEqualIgnoringCase.equalToIgnoringCase(hexString));
    }

    @Test
    void shouldEncodeByteArrayToHexStringUsingBigIntegerStringFormat() {
        byte[] bytes = getSampleBytes();
        String hexString = getSampleHexString();
        String output = hexStringConverter.encodeUsingBigIntegerStringFormat(bytes);
        assertThat(output, IsEqualIgnoringCase.equalToIgnoringCase(hexString));
    }

    @Test
    void shouldDecodeHexStringToByteArrayUsingBigInteger() {
        byte[] bytes = getSampleBytes();
        String hexString = getSampleHexString();
        byte[] output = hexStringConverter.decodeUsingBigInteger(hexString);
        assertArrayEquals(bytes, output);
    }
    
    @Test
    void shouldEncodeByteArrayToHexStringUsingCharacterConversion() {
        byte[] bytes = getSampleBytes();
        String hexString = getSampleHexString();
        String output = hexStringConverter.encodeHexString(bytes);
        assertThat(output, IsEqualIgnoringCase.equalToIgnoringCase(hexString));
    }

    @Test
    void shouldDecodeHexStringToByteArrayUsingCharacterConversion() {
        byte[] bytes = getSampleBytes();
        String hexString = getSampleHexString();
        byte[] output = hexStringConverter.decodeHexString(hexString);
        assertArrayEquals(bytes, output);
    }
    
    @Test
    void shouldDecodeHexToByteWithInvalidHexCharacter() {
        assertThrows(IllegalArgumentException.class, () -> {
            hexStringConverter.hexToByte("fg");
        });
    }

    @Test
    void shouldEncodeByteArrayToHexStringDataTypeConverter() {
        byte[] bytes = getSampleBytes();
        String hexString = getSampleHexString();
        String output = hexStringConverter.encodeUsingDataTypeConverter(bytes);
        assertThat(output, IsEqualIgnoringCase.equalToIgnoringCase(hexString));
    }

    @Test
    void shouldDecodeHexStringToByteArrayUsingDataTypeConverter() {
        byte[] bytes = getSampleBytes();
        String hexString = getSampleHexString();
        byte[] output = hexStringConverter.decodeUsingDataTypeConverter(hexString);
        assertArrayEquals(bytes, output);
    }

    @Test
    void shouldEncodeByteArrayToHexStringUsingGuava() {
        byte[] bytes = getSampleBytes();
        String hexString = getSampleHexString();
        String output = hexStringConverter.encodeUsingGuava(bytes);
        assertThat(output, IsEqualIgnoringCase.equalToIgnoringCase(hexString));
    }

    @Test
    void shouldDecodeHexStringToByteArrayUsingGuava() {
        byte[] bytes = getSampleBytes();
        String hexString = getSampleHexString();
        byte[] output = hexStringConverter.decodeUsingGuava(hexString);
        assertArrayEquals(bytes, output);
    }

    @Test
    void shouldEncodeByteArrayToHexStringUsingApacheCommons() throws DecoderException {
        byte[] bytes = getSampleBytes();
        String hexString = getSampleHexString();
        String output = hexStringConverter.encodeUsingApacheCommons(bytes);
        assertThat(output, IsEqualIgnoringCase.equalToIgnoringCase(hexString));
    }

    @Test
    void shouldDecodeHexStringToByteArrayUsingApacheCommons() throws DecoderException {
        byte[] bytes = getSampleBytes();
        String hexString = getSampleHexString();
        byte[] output = hexStringConverter.decodeUsingApacheCommons(hexString);
        assertArrayEquals(bytes, output);
    }

    private String getSampleHexString() {
        return "0af50c0e2d10";
    }

    private byte[] getSampleBytes() {
        return new byte[] { 10, -11, 12, 14, 45, 16 };
    }

}
