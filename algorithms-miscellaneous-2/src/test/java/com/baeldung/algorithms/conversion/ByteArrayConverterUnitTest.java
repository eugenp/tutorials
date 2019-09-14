package com.baeldung.algorithms.conversion;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.apache.commons.codec.DecoderException;
import org.hamcrest.text.IsEqualIgnoringCase;
import org.junit.Before;
import org.junit.Test;

import com.baeldung.algorithms.conversion.HexStringConverter;

public class ByteArrayConverterUnitTest {

    private HexStringConverter hexStringConverter;

    @Before
    public void setup() {
        hexStringConverter = new HexStringConverter();
    }

    @Test
    public void shouldEncodeByteArrayToHexStringUsingBigIntegerToString() {
        byte[] bytes = getSampleBytes();
        String hexString = getSampleHexString();
        if(hexString.charAt(0) == '0') {
            hexString = hexString.substring(1);
        }
        String output = hexStringConverter.encodeUsingBigIntegerToString(bytes);
        assertThat(output, IsEqualIgnoringCase.equalToIgnoringCase(hexString));
    }

    @Test
    public void shouldEncodeByteArrayToHexStringUsingBigIntegerStringFormat() {
        byte[] bytes = getSampleBytes();
        String hexString = getSampleHexString();
        String output = hexStringConverter.encodeUsingBigIntegerStringFormat(bytes);
        assertThat(output, IsEqualIgnoringCase.equalToIgnoringCase(hexString));
    }

    @Test
    public void shouldDecodeHexStringToByteArrayUsingBigInteger() {
        byte[] bytes = getSampleBytes();
        String hexString = getSampleHexString();
        byte[] output = hexStringConverter.decodeUsingBigInteger(hexString);
        assertArrayEquals(bytes, output);
    }
    
    @Test
    public void shouldEncodeByteArrayToHexStringUsingCharacterConversion() {
        byte[] bytes = getSampleBytes();
        String hexString = getSampleHexString();
        String output = hexStringConverter.encodeHexString(bytes);
        assertThat(output, IsEqualIgnoringCase.equalToIgnoringCase(hexString));
    }

    @Test
    public void shouldDecodeHexStringToByteArrayUsingCharacterConversion() {
        byte[] bytes = getSampleBytes();
        String hexString = getSampleHexString();
        byte[] output = hexStringConverter.decodeHexString(hexString);
        assertArrayEquals(bytes, output);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void shouldDecodeHexToByteWithInvalidHexCharacter() {
        hexStringConverter.hexToByte("fg");
    }

    @Test
    public void shouldEncodeByteArrayToHexStringDataTypeConverter() {
        byte[] bytes = getSampleBytes();
        String hexString = getSampleHexString();
        String output = hexStringConverter.encodeUsingDataTypeConverter(bytes);
        assertThat(output, IsEqualIgnoringCase.equalToIgnoringCase(hexString));
    }

    @Test
    public void shouldDecodeHexStringToByteArrayUsingDataTypeConverter() {
        byte[] bytes = getSampleBytes();
        String hexString = getSampleHexString();
        byte[] output = hexStringConverter.decodeUsingDataTypeConverter(hexString);
        assertArrayEquals(bytes, output);
    }

    @Test
    public void shouldEncodeByteArrayToHexStringUsingGuava() {
        byte[] bytes = getSampleBytes();
        String hexString = getSampleHexString();
        String output = hexStringConverter.encodeUsingGuava(bytes);
        assertThat(output, IsEqualIgnoringCase.equalToIgnoringCase(hexString));
    }

    @Test
    public void shouldDecodeHexStringToByteArrayUsingGuava() {
        byte[] bytes = getSampleBytes();
        String hexString = getSampleHexString();
        byte[] output = hexStringConverter.decodeUsingGuava(hexString);
        assertArrayEquals(bytes, output);
    }

    @Test
    public void shouldEncodeByteArrayToHexStringUsingApacheCommons() throws DecoderException {
        byte[] bytes = getSampleBytes();
        String hexString = getSampleHexString();
        String output = hexStringConverter.encodeUsingApacheCommons(bytes);
        assertThat(output, IsEqualIgnoringCase.equalToIgnoringCase(hexString));
    }

    @Test
    public void shouldDecodeHexStringToByteArrayUsingApacheCommons() throws DecoderException {
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
