package com.baeldung.encoderdecoder;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.joining;

public class EncoderDecoderUnitTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(EncoderDecoderUnitTest.class);
    private static final String testUrl = "http://www.baeldung.com/path+1?key1=value+1&key2=value%40%21%242&key3=value%253#dummy+Fragment";

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
        URI uri = new URI(testUrl);

        Assert.assertThat(uri.getScheme(), CoreMatchers.is("http"));
        Assert.assertThat(uri.getHost(), CoreMatchers.is("www.baeldung.com"));
        Assert.assertThat(uri.getRawPath(), CoreMatchers.is("/path+1"));
        Assert.assertThat(uri.getRawQuery(), CoreMatchers.is("key1=value+1&key2=value%40%21%242&key3=value%253"));
        Assert.assertThat(uri.getRawFragment(), CoreMatchers.is("dummy+Fragment"));
    }

    @Test
    public void givenRequestParam_whenUTF8Scheme_thenEncode() throws Exception {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("key1", "value 1");
        requestParams.put("key2", "value@!$2");
        requestParams.put("key3", "value%3");

        String path = "path+1";
        String fragment = "dummy Fragment";

        String encodedURL = requestParams.keySet().stream().map(key -> key + "=" + encodeValue(requestParams.get(key))).collect(joining("&", "http://www.baeldung.com/" + getPath(path) + "?", "#" + encodeValue(fragment)));

        Assert.assertThat(testUrl, CoreMatchers.is(encodedURL));
    }

    @Test
    public void givenRequestParam_whenUTF8Scheme_thenDecodeRequestParams() throws Exception {
        URI uri = new URI(testUrl);

        String scheme = uri.getScheme();
        String host = uri.getHost();
        String query = uri.getRawQuery();
        String path = uri.getRawPath();
        String fragment = uri.getRawFragment();

        String decodedQuery = Arrays.stream(query.split("&")).map(param -> param.split("=")[0] + "=" + decode(param.split("=")[1])).collect(joining("&"));
        String decodedPath = Arrays.stream(path.split("/")).map(param -> getPath(param)).collect(joining("/"));

        Assert.assertEquals("http://www.baeldung.com/path+1?key1=value 1&key2=value@!$2&key3=value%3#dummy Fragment", scheme + "://" + host + decodedPath + "?" + decodedQuery + "#" + decode(fragment));
    }

    private String getPath(String path) {
        try {
            path = new URI(null, null, path, null).getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return path;
    }

    @Test
    public void givenPath_thenEncodeDecodePath() throws URISyntaxException {
        URI uri = new URI(null, null, "/Path 1/Path+2", null);

        Assert.assertEquals("/Path 1/Path+2", uri.getPath());
        Assert.assertEquals("/Path%201/Path+2", uri.getRawPath());
    }
}
