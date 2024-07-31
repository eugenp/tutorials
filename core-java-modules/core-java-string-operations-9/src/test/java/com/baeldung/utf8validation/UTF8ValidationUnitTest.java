package com.baeldung.utf8validation;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import org.apache.tika.detect.EncodingDetector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.txt.UniversalEncodingDetector;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.CharBuffer;
import java.nio.charset.*;

import static org.junit.jupiter.api.Assertions.*;

class UTF8ValidationUnitTest {

    private static final String UTF8_STRING = "Hello 你好";

    private static final byte[] UTF8_BYTES = UTF8_STRING.getBytes(StandardCharsets.UTF_8);

    private static final byte[] INVALID_UTF8_BYTES = {(byte) 0xF0, (byte) 0xC1, (byte) 0x8C, (byte) 0xBC, (byte) 0xD1};

    private static final InputStream ENGLISH_INPUTSTREAM = new ByteArrayInputStream("Hello".getBytes(StandardCharsets.UTF_8));

    private static final InputStream UTF8_INPUTSTREAM = new ByteArrayInputStream(UTF8_BYTES);

    private static final InputStream INVALID_UTF8_INPUTSTREAM = new ByteArrayInputStream(INVALID_UTF8_BYTES);

    @Test
    void whenConvertValidUTF8BytesToString_thenReturnExpectedString() {
        String decodedStr = new String(UTF8_BYTES, StandardCharsets.UTF_8);
        assertEquals(UTF8_STRING, decodedStr);
    }

    @Test
    void whenConvertInvalidUTF8BytesToString_thenReturnReplacementCharacters() {
        String decodedStr = new String(INVALID_UTF8_BYTES, StandardCharsets.UTF_8);
        assertEquals("�����", decodedStr);
    }

    @Test
    void whenDecodeValidUTF8Bytes_thenSucceeds() throws CharacterCodingException {

        CharsetDecoder charsetDecoder = StandardCharsets.UTF_8.newDecoder();
        CharBuffer decodedCharBuffer = charsetDecoder.decode(java.nio.ByteBuffer.wrap(UTF8_BYTES));
        assertEquals(UTF8_STRING, decodedCharBuffer.toString());
    }

    @Test
    void whenDecodeInvalidUTF8Bytes_thenThrowsMalformedInputException() {

        CharsetDecoder charsetDecoder = StandardCharsets.UTF_8.newDecoder();
        assertThrows(MalformedInputException.class,() -> {charsetDecoder.decode(java.nio.ByteBuffer.wrap(INVALID_UTF8_BYTES));});
    }

    @Test
    void whenValidateValidInputStreamByTika_thenReturnsUTF8() throws IOException {

        EncodingDetector encodingDetector = new UniversalEncodingDetector();
        Charset detectedCharset = encodingDetector.detect(UTF8_INPUTSTREAM, new Metadata());
        assertEquals(StandardCharsets.UTF_8, detectedCharset);
    }

    @Test
    void whenValidateValidEnglishInputStreamByTika_thenReturnsISO_88591_1() throws IOException {

        EncodingDetector encodingDetector = new UniversalEncodingDetector();
        Charset detectedCharset = encodingDetector.detect(ENGLISH_INPUTSTREAM, new Metadata());
        assertEquals(StandardCharsets.ISO_8859_1, detectedCharset);
    }

    @Test
    void whenValidateInvalidInputStreamByTika_thenReturnsNull() throws IOException {

        EncodingDetector encodingDetector = new UniversalEncodingDetector();
        Charset detectedCharset = encodingDetector.detect(INVALID_UTF8_INPUTSTREAM, new Metadata());
        assertNull(detectedCharset);
    }

    @Test
    void whenValidateValidInputStreamByICU4J_thenReturnsUTF8() throws IOException {

        CharsetDetector detector = new CharsetDetector();
        detector.setText(UTF8_INPUTSTREAM);
        CharsetMatch charsetMatch = detector.detect();
        assertEquals(StandardCharsets.UTF_8.name(), charsetMatch.getName());
    }

    @Test
    void whenValidateValidEnglishInputStreamByICU4J_thenReturnsISO_8859_1() throws IOException {

        CharsetDetector detector = new CharsetDetector();
        detector.setText(ENGLISH_INPUTSTREAM);
        CharsetMatch charsetMatch = detector.detect();
        assertEquals(StandardCharsets.ISO_8859_1.name(), charsetMatch.getName());
    }

    @Test
    void whenValidateValidInputStreamByICU4J_thenReturnsNotEqualToUTF_8() throws IOException {

        CharsetDetector detector = new CharsetDetector();
        detector.setText(INVALID_UTF8_INPUTSTREAM);
        CharsetMatch charsetMatch = detector.detect();
        assertNotEquals(StandardCharsets.UTF_8.name(), charsetMatch.getName());
    }

}