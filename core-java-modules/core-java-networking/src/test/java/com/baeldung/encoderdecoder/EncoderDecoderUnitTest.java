package com.baeldung.encoderdecoder;

import static java.util.stream.Collectors.joining;
import static org.hamcrest.CoreMatchers.is;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriUtils;

public class EncoderDecoderUnitTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(EncoderDecoderUnitTest.class);
    private static final String testUrl = "http://www.baeldung.com?key1=value+1&key2=value%40%21%242&key3=value%253";
    private static final String testUrlWithPath = "http://www.baeldung.com/path+1?key1=value+1&key2=value%40%21%242&key3=value%253";

    private String encodeValue(String value) {
        String encoded = null;
        try {
            encoded = URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Error encoding parameter {}", e.getMessage(), e);
        }
        return encoded;
    }

    private String decode(String value) {
        String decoded = null;
        try {
            decoded = URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Error encoding parameter {}", e.getMessage(), e);
        }
        return decoded;
    }

    @Test
    public void givenURL_whenAnalyze_thenCorrect() throws Exception {
        URL url = new URL(testUrl);

        Assert.assertThat(url.getProtocol(), is("http"));
        Assert.assertThat(url.getHost(), is("www.baeldung.com"));
        Assert.assertThat(url.getQuery(), is("key1=value+1&key2=value%40%21%242&key3=value%253"));
    }

    @Test
    public void givenRequestParam_whenUTF8Scheme_thenEncode() throws Exception {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("key1", "value 1");
        requestParams.put("key2", "value@!$2");
        requestParams.put("key3", "value%3");

        String encodedURL = requestParams.keySet().stream().map(key -> key + "=" + encodeValue(requestParams.get(key))).collect(joining("&", "http://www.baeldung.com?", ""));

        Assert.assertThat(testUrl, is(encodedURL));
    }

    @Test
    public void givenRequestParam_whenUTF8Scheme_thenDecodeRequestParams() throws Exception {
        URL url = new URL(testUrl);

        String query = url.getQuery();

        String decodedQuery = Arrays.stream(query.split("&")).map(param -> param.split("=")[0] + "=" + decode(param.split("=")[1])).collect(joining("&"));

        Assert.assertEquals("http://www.baeldung.com?key1=value 1&key2=value@!$2&key3=value%3", url.getProtocol() + "://" + url.getHost() + "?" + decodedQuery);
    }

    private String encodePath(String path) {
        try {
            path = UriUtils.encodePath(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Error encoding parameter {}", e.getMessage(), e);
        }
        return path;
    }

    @Test
    public void givenPathSegment_thenEncodeDecode() throws UnsupportedEncodingException {
        String pathSegment = "/Path 1/Path+2";
        String encodedPathSegment = encodePath(pathSegment);
        String decodedPathSegment = UriUtils.decode(encodedPathSegment, "UTF-8");
        Assert.assertEquals("/Path%201/Path+2", encodedPathSegment);
        Assert.assertEquals("/Path 1/Path+2", decodedPathSegment);
    }

    @Test
    public void givenPathAndRequestParam_whenUTF8Scheme_thenEncode() throws Exception {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("key1", "value 1");
        requestParams.put("key2", "value@!$2");
        requestParams.put("key3", "value%3");

        String path = "path+1";

        String encodedURL = requestParams.keySet().stream().map(key -> key + "=" + encodeValue(requestParams.get(key))).collect(joining("&", "http://www.baeldung.com/" + encodePath(path) + "?", ""));

        Assert.assertThat(testUrlWithPath, is(encodedURL));
    }

}
