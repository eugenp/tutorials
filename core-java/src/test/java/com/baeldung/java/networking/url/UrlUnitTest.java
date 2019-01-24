package com.baeldung.java.networking.url;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

/**
 * 测试：Url
 */
public class UrlUnitTest {

    @Test
    public void givenUrl_whenCanIdentifyProtocol_thenCorrect() throws MalformedURLException {
        final URL url = new URL("http://baeldung.com");
        System.out.println("url.getProtocol():{}" + url.getProtocol());
        assertEquals("http", url.getProtocol());
    }

    @Test
    public void givenUrl_whenCanGetHost_thenCorrect() throws MalformedURLException {
        final URL url = new URL("http://baeldung.com");
        System.out.println("url.getHost():{}" + url.getHost());
        assertEquals("baeldung.com", url.getHost());
    }

    @Test
    public void givenUrl_whenCanGetFileName_thenCorrect2() throws MalformedURLException {
        final URL url = new URL("http://baeldung.com/articles?topic=java&version=8");
        System.out.println("url.getFile():{}" + url.getFile());
        assertEquals("/articles?topic=java&version=8", url.getFile());
    }

    @Test
    public void givenUrl_whenCanGetFileName_thenCorrect1() throws MalformedURLException {
        final URL url = new URL("http://baeldung.com/guidelines.txt");
        System.out.println("url.getFile():{}" + url.getFile());
        assertEquals("/guidelines.txt", url.getFile());
    }

    @Test
    public void givenUrl_whenCanGetPathParams_thenCorrect() throws MalformedURLException {
        final URL url = new URL("http://baeldung.com/articles?topic=java&version=8");
        System.out.println("url.getPath():{}" + url.getPath());
        assertEquals("/articles", url.getPath());
    }

    @Test
    public void givenUrl_whenCanGetQueryParams_thenCorrect() throws MalformedURLException {
        final URL url = new URL("http://baeldung.com/articles?topic=java");
        System.out.println("url.getQuery():{}" + url.getQuery());
        assertEquals("topic=java", url.getQuery());
    }

    @Test
    public void givenUrl_whenGetsDefaultPort_thenCorrect() throws MalformedURLException {
        final URL url = new URL("http://baeldung.com");
        System.out.println("url.getPort():{}" + url.getPort());
        System.out.println("url.getDefaultPort():{}" + url.getDefaultPort());
        assertEquals(-1, url.getPort());
        assertEquals(80, url.getDefaultPort());
    }

    @Test
    public void givenUrl_whenGetsPort_thenCorrect() throws MalformedURLException {
        final URL url = new URL("http://baeldung.com:8090");
        System.out.println("url.getPort():{}" + url.getPort());
        assertEquals(8090, url.getPort());
        assertEquals(80, url.getDefaultPort());
    }

    @Test
    public void givenBaseUrl_whenCreatesRelativeUrl_thenCorrect() throws MalformedURLException {
        final URL baseUrl = new URL("http://baeldung.com");
        final URL relativeUrl = new URL(baseUrl, "a-guide-to-java-sockets");
        System.out.println("relativeUrl.toString():{}" + relativeUrl.toString());
        assertEquals("http://baeldung.com/a-guide-to-java-sockets", relativeUrl.toString());
    }

    /**
     * 当给出了绝对路径时，会忽略基本路径。
     * @throws MalformedURLException
     */
    @Test
    public void givenAbsoluteUrl_whenIgnoresBaseUrl_thenCorrect() throws MalformedURLException {
        final URL baseUrl = new URL("http://baeldung.com");
        final URL relativeUrl = new URL(baseUrl, "http://baeldung.com/a-guide-to-java-sockets");
        System.out.println("relativeUrl.toString():{}" + relativeUrl.toString());
        assertEquals("http://baeldung.com/a-guide-to-java-sockets", relativeUrl.toString());
    }

    @Test
    public void givenUrlComponents_whenConstructsCompleteUrl_thenCorrect() throws MalformedURLException {
        final String protocol = "http";
        final String host = "baeldung.com";
        final String file = "/guidelines.txt";
        final URL url = new URL(protocol, host, file);
        System.out.println("url.toString():{}" + url.toString());
        assertEquals("http://baeldung.com/guidelines.txt", url.toString());
    }

    @Test
    public void givenUrlComponents_whenConstructsCompleteUrl_thenCorrect2() throws MalformedURLException {
        final String protocol = "http";
        final String host = "baeldung.com";
        final String file = "/articles?topic=java&version=8";
        final URL url = new URL(protocol, host, file);
        System.out.println("url.toString():{}" + url.toString());
        assertEquals("http://baeldung.com/articles?topic=java&version=8", url.toString());
    }

    @Test
    public void givenUrlComponentsWithPort_whenConstructsCompleteUrl_thenCorrect() throws MalformedURLException {
        final String protocol = "http";
        final String host = "baeldung.com";
        final int port = 9000;
        final String file = "/guidelines.txt";
        final URL url = new URL(protocol, host, port, file);
        System.out.println("url.toString():{}" + url.toString());
        assertEquals("http://baeldung.com:9000/guidelines.txt", url.toString());
    }

}
