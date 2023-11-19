package com.baeldung.networking.url;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.collect.ImmutableMap;

public class UrlUnitTest {

    @Test
    public void givenUrl_whenCanIdentifyProtocol_thenCorrect() throws MalformedURLException, URISyntaxException {
        final URL url = new URI("http://baeldung.com").toURL();
        assertEquals("http", url.getProtocol());
    }

    @Test
    public void givenUrl_whenCanGetHost_thenCorrect() throws MalformedURLException, URISyntaxException {
        final URL url = new URI("http://baeldung.com").toURL();
        assertEquals("baeldung.com", url.getHost());
    }

    @Test
    public void givenUrl_whenCanGetFileName_thenCorrect2() throws MalformedURLException, URISyntaxException {
        final URL url = new URI("http://baeldung.com/articles?topic=java&version=8").toURL();
        assertEquals("/articles?topic=java&version=8", url.getFile());
    }

    @Test
    public void givenUrl_whenCanGetFileName_thenCorrect1() throws MalformedURLException, URISyntaxException {
        final URL url = new URI("http://baeldung.com/guidelines.txt").toURL();
        assertEquals("/guidelines.txt", url.getFile());
    }

    @Test
    public void givenUrl_whenCanGetPathParams_thenCorrect() throws MalformedURLException, URISyntaxException {
        final URL url = new URI("http://baeldung.com/articles?topic=java&version=8").toURL();
        assertEquals("/articles", url.getPath());
    }

    @Test
    public void givenUrl_whenCanGetQueryParams_thenCorrect() throws MalformedURLException, URISyntaxException {
        final URL url = new URI("http://baeldung.com/articles?topic=java&amp;version=8").toURL();
        assertEquals("topic=java&amp;version=8", url.getQuery());
    }

    @Test
    public void givenUrl_whenGetsDefaultPort_thenCorrect() throws MalformedURLException, URISyntaxException {
        final URL url = new URI("http://baeldung.com").toURL();
        assertEquals(-1, url.getPort());
        assertEquals(80, url.getDefaultPort());
    }

    @Test
    public void givenUrl_whenGetsPort_thenCorrect() throws MalformedURLException, URISyntaxException {
        final URL url = new URI("http://baeldung.com:8090").toURL();
        assertEquals(8090, url.getPort());
        assertEquals(80, url.getDefaultPort());
    }

    @Test
    public void givenHomeUrlAndFullUrl_whenRelativize_thenCorrect() throws MalformedURLException, URISyntaxException {
        final URI homeUri = new URI("http://baeldung.com");
        final URI fullUri = new URI("http://baeldung.com" + "/a-guide-to-java-sockets");
        final URI relativeUri = homeUri.relativize(fullUri);
        assertEquals("a-guide-to-java-sockets", relativeUri.toString());
    }

    @Test
    public void givenUrlComponents_whenConstructsCompleteUrl_thenCorrect() throws MalformedURLException, URISyntaxException {
        final String protocol = "http";
        final String host = "baeldung.com";
        final String file = "/guidelines.txt";
        final String fragment = "myImage";
        final URL url = new URI(protocol, host, file, fragment).toURL();
        assertEquals("http://baeldung.com/guidelines.txt#myImage", url.toString());
    }

    @Test
    public void givenUrlComponents_whenConstructsCompleteUrl_thenCorrect2() throws MalformedURLException, URISyntaxException {
        final String protocol = "http";
        final String username = "admin";
        final String host = "baeldung.com";
        final String file = "/articles";
        final String query = "topic=java&version=8";
        final String fragment = "myImage";
        final URL url = new URI(protocol, username, host, -1, file, query, fragment).toURL();
        assertEquals("http://admin@baeldung.com/articles?topic=java&version=8#myImage", url.toString());
    }

    @Test
    public void givenRelativeUrl_whenCreatesRelativeUrl_thenThrows() throws URISyntaxException, MalformedURLException {
        final URI uri = new URI("/a-guide-to-java-sockets");
        Assert.assertThrows(IllegalArgumentException.class, () -> uri.toURL());
    }

    @Test
    public void givenUrlComponentsWithPort_whenConstructsCompleteUrl_thenCorrect() throws MalformedURLException, URISyntaxException {
        final String protocol = "http";
        final String username = "admin";
        final String host = "baeldung.com";
        final int port = 9000;
        final String file = "/guidelines.txt";
        final String fragment = "myImage";
        final URL url = new URI(protocol, username, host, port, file, null, fragment).toURL();
        assertEquals("http://admin@baeldung.com:9000/guidelines.txt#myImage", url.toString());
    }

    @Test
    public void givenUrlParameters_whenBuildUrlWithURIBuilder_thenSuccess() throws URISyntaxException, MalformedURLException {
        URIBuilder uriBuilder = new URIBuilder("http://baeldung.com/articles");
        uriBuilder.setPort(9090);
        uriBuilder.addParameter("topic", "java");
        uriBuilder.addParameter("version", "8");
        URL url = uriBuilder.build()
            .toURL();
        assertEquals("http://baeldung.com:9090/articles?topic=java&version=8", url.toString());
    }

    @Test
    public void givenUrlParametersInMap_whenBuildUrlWithURIBuilder_thenSuccess() throws URISyntaxException, MalformedURLException {
        Map<String, String> paramMap = ImmutableMap.of("topic", "java", "version", "8");
        URIBuilder uriBuilder = new URIBuilder("http://baeldung.com/articles");
        uriBuilder.setPort(9090);
        uriBuilder.addParameters(paramMap.entrySet()
            .stream()
            .map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue()))
            .collect(toList()));

        URL url = uriBuilder.build()
            .toURL();
        assertEquals("http://baeldung.com:9090/articles?topic=java&version=8", url.toString());
    }

    @Test
    public void givenUrlParameters_whenBuildUrlWithSpringUriComponentsBuilder_thenSuccess() throws MalformedURLException {
        URL url = UriComponentsBuilder.newInstance()
            .scheme("http")
            .host("baeldung.com")
            .port(9090)
            .path("articles")
            .queryParam("topic", "java")
            .queryParam("version", "8")
            .build()
            .toUri()
            .toURL();

        assertEquals("http://baeldung.com:9090/articles?topic=java&version=8", url.toString());
    }

    @Test
    public void givenURI_whenConvertingToURL_thenCorrect() throws IOException, URISyntaxException {
        String aURIString = "http://courses.baeldung.com";
        URI uri = new URI(aURIString);
        URL url = uri.toURL();
        assertNotNull(url);
        assertEquals(aURIString, url.toString());
    }

    @Test
    public void givenPath_whenConvertingToURIAndThenURL_thenCorrect() throws IOException, URISyntaxException {
        String finalPath = "file:/D:/baeldung/java-url";
        Path path = Paths.get("/baeldung/java-url");
        URI uri = path.toUri();
        URL url = uri.toURL();
        assertNotNull(url);
        // Adapt the finalPath value to match your own path
        // assertEquals(finalPath, url.toString());
    }

}