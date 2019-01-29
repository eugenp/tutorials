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
import java.util.StringJoiner;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriUtils;

/**
 * @see java.net.URLEncoder#encode(String, String)
 * @see java.net.URLDecoder#decode(String, String)
 */
public class EncoderDecoderUnitTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(EncoderDecoderUnitTest.class);
    private static final String testUrl = "http://www.baeldung.com?key1=value+1&key2=value%40%21%242&key3=value%253";
    private static final String testUrlWithPath = "http://www.baeldung.com/path+1?key1=value+1&key2=value%40%21%242&key3=value%253";

    /**
     * 编码
     * @param value
     * @return
     */
    private String encodeValue(String value) {
        String encoded = null;
        try {
            encoded = URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        }
        catch (UnsupportedEncodingException e) {
            LOGGER.error("Error encoding parameter {}", e.getMessage(), e);
        }
        return encoded;
    }

    /**
     * 解码
     * @param value
     * @return
     */
    private String decode(String value) {
        String decoded = null;
        try {
            decoded = URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
        }
        catch (UnsupportedEncodingException e) {
            LOGGER.error("Error encoding parameter {}", e.getMessage(), e);
        }
        return decoded;
    }

    /**
     * 解析url是否正确
     * @throws Exception
     */
    @Test
    public void givenURL_whenAnalyze_thenCorrect() throws Exception {
        URL url = new URL(testUrl);

        Assert.assertThat(url.getProtocol(), is("http"));
        Assert.assertThat(url.getHost(), is("www.baeldung.com"));
        Assert.assertThat(url.getQuery(), is("key1=value+1&key2=value%40%21%242&key3=value%253"));
    }

    /**
     * @see java.util.StringJoiner#add(CharSequence newElement)
     * @throws Exception
     */
    @Test
    public void givenRequestParam_whenUTF8Scheme_thenEncode() throws Exception {
        Map<String, String> requestParams = new HashMap<String, String>();
        requestParams.put("key1", "value 1");
        requestParams.put("key2", "value@!$2");
        requestParams.put("key3", "value%3");

        StringJoiner joiner = new StringJoiner("&", "http://www.baeldung.com?", "");
        for (String key : requestParams.keySet()) {
            String s = key + "=" + encodeValue(requestParams.get(key));
            joiner.add(s);
        }
        String encodedURL = joiner.toString();

        Assert.assertThat(testUrl, is(encodedURL));
    }

    @Test
    public void givenRequestParam_whenUTF8Scheme_thenDecodeRequestParams() throws Exception {
        URL url = new URL(testUrl);

        String query = url.getQuery();
        System.out.println("query:{}" + query);

        StringJoiner joiner = new StringJoiner("&");
        for (String param : query.split("&")) {
            String s = param.split("=")[0] + "=" + decode(param.split("=")[1]);
            joiner.add(s);
        }
        String decodedQuery = joiner.toString();

        Assert.assertEquals("http://www.baeldung.com?key1=value 1&key2=value@!$2&key3=value%3", url.getProtocol() + "://" + url.getHost() + "?" + decodedQuery);
    }

    /**
     * @see org.springframework.web.util.UriUtils#encodePath(String path, String encoding)
     * @param path
     * @return
     */
    private String encodePath(String path) {
        try {
            path = UriUtils.encodePath(path, "UTF-8");
            //UnsupportedEncodingException
        }
        catch (Exception e) {
            LOGGER.error("Error encoding parameter {}", e.getMessage(), e);
        }
        return path;
    }

    /**
     * @see org.springframework.web.util.UriUtils#decode(String source, String encoding)
     * @throws UnsupportedEncodingException
     */
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
        Map<String, String> requestParams = new HashMap<String, String>();
        requestParams.put("key1", "value 1");
        requestParams.put("key2", "value@!$2");
        requestParams.put("key3", "value%3");

        String path = "path+1";

        StringJoiner joiner = new StringJoiner("&", "http://www.baeldung.com/" + encodePath(path) + "?", "");
        for (String key : requestParams.keySet()) {
            String s = key + "=" + encodeValue(requestParams.get(key));
            joiner.add(s);
        }
        String encodedURL = joiner.toString();

        System.out.println("encodedURL:{}" + encodedURL);
        Assert.assertThat(testUrlWithPath, is(encodedURL));
    }

}
