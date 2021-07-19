package com.baeldung.networking.uriurl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import org.apache.commons.io.IOUtils;
import static org.junit.Assert.*;
import org.junit.Test;

public class URIvsURLUnitTest {

    @Test
    public void whenCreatingURIs_thenSameInfo() throws URISyntaxException {
        URI firstURI = new URI("somescheme://theuser:thepassword@someauthority:80/some/path?thequery#somefragment");
        URI secondURI = new URI("somescheme", "theuser:thepassword", "someuthority", 80, "/some/path", "thequery", "somefragment");

        assertEquals(firstURI.getScheme(), secondURI.getScheme());
        assertEquals(firstURI.getPath(), secondURI.getPath());
    }

    @Test
    public void whenCreatingURLs_thenSameInfo() throws MalformedURLException {
        URL firstURL = new URL("http://theuser:thepassword@somehost:80/path/to/file?thequery#somefragment");
        URL secondURL = new URL("http", "somehost", 80, "/path/to/file");

        assertEquals(firstURL.getHost(), secondURL.getHost());
        assertEquals(firstURL.getPath(), secondURL.getPath());
    }

    @Test
    public void whenCreatingURI_thenCorrect() {
        URI uri = URI.create("urn:isbn:1234567890");

        assertNotNull(uri);
    }

    @Test(expected = MalformedURLException.class)
    public void whenCreatingURLs_thenException() throws MalformedURLException {
        URL theURL = new URL("otherprotocol://somehost/path/to/file");

        assertNotNull(theURL);
    }

    @Test
    public void givenObjects_whenConverting_thenCorrect() throws MalformedURLException, URISyntaxException {
        String aURIString = "http://somehost:80/path?thequery";
        URI uri = new URI(aURIString);
        URL url = new URL(aURIString);

        URL toURL = uri.toURL();
        URI toURI = url.toURI();

        assertNotNull(url);
        assertNotNull(uri);
        assertEquals(toURL.toString(), toURI.toString());
    }

    @Test(expected = MalformedURLException.class)
    public void givenURI_whenConvertingToURL_thenException() throws MalformedURLException, URISyntaxException {
        URI uri = new URI("somescheme://someauthority/path?thequery");

        URL url = uri.toURL();

        assertNotNull(url);
    }

    @Test
    public void givenURL_whenGettingContents_thenCorrect() throws MalformedURLException, IOException {
        URL url = new URL("http://courses.baeldung.com");

        String contents = IOUtils.toString(url.openStream());
    } 
}
