package com.baeldung.converter;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

import java.util.Random;

import org.apache.commons.codec.DecoderException;
import org.hamcrest.text.IsEqualIgnoringCase;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.base.Stopwatch;

public class ByteArrayConverterTest {

    private ByteArrayConverter byteArrayConverter;

    @Before
    public void setup() {
        byteArrayConverter = new ByteArrayConverter();
    }

    @Test
    @Ignore("Ignoring as due to leading 0 problem woth Big Integer conversion")
    public void shouldEncodeByteArrayToHexStringUsingBigIntegerToString() {
        byte[] bytes = getSampleBytes();
        String hexString = getSampleHexString();
        String output = byteArrayConverter.encodeUsingBigIntegerToString(bytes);
        assertThat(output, IsEqualIgnoringCase.equalToIgnoringCase(hexString));
    }

    @Test
    @Ignore("Ignoring as due to leading 0 problem woth Big Integer conversion")
    public void shouldEncodeByteArrayToHexStringUsingBigIntegerStringFormat() {
        byte[] bytes = getSampleBytes();
        String hexString = getSampleHexString();
        String output = byteArrayConverter.encodeUsingBigIntegerToString(bytes);
        assertThat(output, IsEqualIgnoringCase.equalToIgnoringCase(hexString));
    }

    @Test
    public void shouldDecodeHexStringToByteArrayUsingBigInteger() {
        byte[] bytes = getSampleBytes();
        String hexString = getSampleHexString();
        byte[] output = byteArrayConverter.decodeUsingBigInteger(hexString);
        assertArrayEquals(bytes, output);
    }

    @Test
    public void shouldEncodeByteArrayToHexStringUsingCharacterConversion() {
        byte[] bytes = getSampleBytes();
        String hexString = getSampleHexString();
        String output = byteArrayConverter.encodeToHexString(bytes);
        assertThat(output, IsEqualIgnoringCase.equalToIgnoringCase(hexString));
    }

    @Test
    public void shouldDecodeHexStringToByteArrayUsingCharacterConversion() {
        byte[] bytes = getSampleBytes();
        String hexString = getSampleHexString();
        byte[] output = byteArrayConverter.decodeHexString(hexString);
        assertArrayEquals(bytes, output);
    }

    @Test
    public void shouldEncodeByteArrayToHexStringDataTypeConverter() {
        byte[] bytes = getSampleBytes();
        String hexString = getSampleHexString();
        String output = byteArrayConverter.encodeUsingDataTypeConverter(bytes);
        assertThat(output, IsEqualIgnoringCase.equalToIgnoringCase(hexString));
    }

    @Test
    public void shouldDecodeHexStringToByteArrayUsingDataTypeConverter() {
        byte[] bytes = getSampleBytes();
        String hexString = getSampleHexString();
        byte[] output = byteArrayConverter.decodeUsingDataTypeConverter(hexString);
        assertArrayEquals(bytes, output);
    }

    @Test
    public void shouldEncodeByteArrayToHexStringUsingGuava() {
        byte[] bytes = getSampleBytes();
        String hexString = getSampleHexString();
        String output = byteArrayConverter.encodeUsingGuava(bytes);
        assertThat(output, IsEqualIgnoringCase.equalToIgnoringCase(hexString));
    }

    @Test
    public void shouldDecodeHexStringToByteArrayUsingGuava() {
        byte[] bytes = getSampleBytes();
        String hexString = getSampleHexString();
        byte[] output = byteArrayConverter.decodeUsingGuava(hexString);
        assertArrayEquals(bytes, output);
    }

    @Test
    public void shouldEncodeByteArrayToHexStringUsingApacheCommons() throws DecoderException {
        byte[] bytes = getSampleBytes();
        String hexString = getSampleHexString();
        String output = byteArrayConverter.encodeUsingApacheCommons(bytes);
        assertThat(output, IsEqualIgnoringCase.equalToIgnoringCase(hexString));
    }

    @Test
    public void shouldDecodeHexStringToByteArrayUsingApacheCommons() throws DecoderException {
        byte[] bytes = getSampleBytes();
        String hexString = getSampleHexString();
        byte[] output = byteArrayConverter.decodeUsingApacheCommons(hexString);
        assertArrayEquals(bytes, output);
    }

    private String getSampleHexString() {
        return "0af50c0e2d10";
    }

    private byte[] getSampleBytes() {
        return new byte[] { 10, -11, 12, 14, 45, 16 };
    }

    private byte[] getRandomBytes(int size) {
        byte[] bytes = new byte[size];
        new Random().nextBytes(bytes);
        return bytes;
    }

    private String getRandomHexString(int size) {
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        while (buffer.length() < size) {
            buffer.append(Integer.toHexString(random.nextInt()));
        }
        return buffer.toString()
            .substring(0, size);
    }

    @Test
    public void encodingPerformanceComparisionTest() throws DecoderException {
        byte[] bytes = getRandomBytes(4000);
        System.out.println("Performance for encoding Sample set of " + bytes.length);
        Stopwatch stopwatch = Stopwatch.createStarted();
        byteArrayConverter.encodeToHexString(bytes);
        System.out.println("Time taken by Character Conversion\t" + stopwatch);
        stopwatch.reset();
        stopwatch.start();
        byteArrayConverter.encodeUsingBigIntegerToString(bytes);
        System.out.println("Time taken by BigInteger toString\t" + stopwatch);
        stopwatch.reset();
        stopwatch.start();
        byteArrayConverter.encodeUsingBigIntegerStringFormat(bytes);
        System.out.println("Time taken by BigInteger String Format\t" + stopwatch);
        stopwatch.reset();
        stopwatch.start();
        byteArrayConverter.encodeUsingDataTypeConverter(bytes);
        System.out.println("Time taken by Data Type Converter\t" + stopwatch);
        stopwatch.reset();
        stopwatch.start();
        byteArrayConverter.encodeUsingApacheCommons(bytes);
        System.out.println("Time taken by Apache Commons\t\t" + stopwatch);
        stopwatch.reset();
        stopwatch.start();
        byteArrayConverter.encodeUsingGuava(bytes);
        System.out.println("Time taken by Guava\t\t\t" + stopwatch);
        stopwatch.stop();
    }

    @Test
    public void decodingPerformanceComparisionTest() throws DecoderException {
        String hexString = getRandomHexString(2000);
        System.out.println("Performance for decoding Sample set of " + hexString.length());
        Stopwatch stopwatch = Stopwatch.createStarted();
        byteArrayConverter.decodeHexString(hexString);
        System.out.println("Time taken by Character Conversion\t" + stopwatch);
        stopwatch.reset();
        stopwatch.start();
        byteArrayConverter.decodeUsingBigInteger(hexString);
        System.out.println("Time taken by BigInteger\t\t" + stopwatch);
        stopwatch.reset();
        stopwatch.start();
        byteArrayConverter.decodeUsingDataTypeConverter(hexString);
        System.out.println("Time taken by Data Type Converter\t" + stopwatch);
        stopwatch.reset();
        stopwatch.start();
        byteArrayConverter.decodeUsingApacheCommons(hexString);
        System.out.println("Time taken by Apache Commons\t\t" + stopwatch);
        stopwatch.reset();
        stopwatch.start();
        byteArrayConverter.decodeUsingGuava(hexString);
        System.out.println("Time taken by Guava\t\t\t" + stopwatch);
        stopwatch.stop();
    }

}
