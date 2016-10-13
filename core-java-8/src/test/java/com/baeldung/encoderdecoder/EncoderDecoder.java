package com.baeldung.encoderdecoder;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class EncoderDecoder {

    @Test
    public void givenPlainURL_whenUsingUTF8EncodingScheme_thenEncodeURL() throws UnsupportedEncodingException {
        String plainURL = "http://www.baeldung.com" ;
        String encodedURL = URLEncoder.encode(plainURL, StandardCharsets.UTF_8.toString());

        Assert.assertThat("http%3A%2F%2Fwww.baeldung.com", CoreMatchers.is(encodedURL));
    }

    @Test
    public void givenEncodedURL_whenUsingUTF8EncodingScheme_thenDecodeURL() throws UnsupportedEncodingException {
        String encodedURL = "http%3A%2F%2Fwww.baeldung.com" ;
        String decodedURL = URLDecoder.decode(encodedURL, StandardCharsets.UTF_8.toString());

        Assert.assertThat("http://www.baeldung.com", CoreMatchers.is(decodedURL));
    }

    @Test
    public void givenEncodedURL_whenUsingWrongEncodingScheme_thenDecodeInvalidURL() throws UnsupportedEncodingException {
        String encodedURL = "http%3A%2F%2Fwww.baeldung.com";

        String decodedURL = URLDecoder.decode(encodedURL, StandardCharsets.UTF_16.toString());

        Assert.assertFalse("http://www.baeldung.com".equals(decodedURL));
    }
}
