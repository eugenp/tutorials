package com.baeldung.networking.uriurl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.networking.uriurl.URLDemo;

@FixMethodOrder
public class URLDemoLiveTest {
    private final Logger log = LoggerFactory.getLogger(URLDemoLiveTest.class);
    static String URLSTRING = "https://wordpress.org:443/support/topic/page-jumps-within-wordpress/?replies=3#post-2278484";
    // parsed locator
    static String URLPROTOCOL = "https";
    String URLAUTHORITY = "wordpress.org:443";
    static String URLHOST = "wordpress.org";
    static String URLPATH = "/support/topic/page-jumps-within-wordpress/";
    String URLFILENAME = "/support/topic/page-jumps-within-wordpress/?replies=3";
    int URLPORT = 443;
    static int URLDEFAULTPORT = 443;
    static String URLQUERY = "replies=3";
    static String URLREFERENCE = "post-2278484";
    static String URLCOMPOUND = URLPROTOCOL + "://" + URLHOST + ":" + URLDEFAULTPORT + URLPATH + "?" + URLQUERY + "#" + URLREFERENCE;

    static URL url;
    URLConnection urlConnection = null;
    HttpURLConnection connection = null;
    BufferedReader in = null;
    String urlContent = "";

    @BeforeClass
    public static void givenEmplyURL_whenInitializeURL_thenSuccess() throws MalformedURLException {
        url = new URL(URLCOMPOUND);
    }

    // check parsed URL
    @Test
    public void givenURL_whenURLIsParsed_thenSuccess() {
        assertNotNull("URL is null", url);
        assertEquals("URL string is not equal", url.toString(), URLSTRING);
        assertEquals("Protocol is not equal", url.getProtocol(), URLPROTOCOL);
        assertEquals("Authority is not equal", url.getAuthority(), URLAUTHORITY);
        assertEquals("Host string is not equal", url.getHost(), URLHOST);
        assertEquals("Path string is not equal", url.getPath(), URLPATH);
        assertEquals("File string is not equal", url.getFile(), URLFILENAME);
        assertEquals("Port number is not equal", url.getPort(), URLPORT);
        assertEquals("Default port number is not equal", url.getDefaultPort(), URLDEFAULTPORT);
        assertEquals("Query string is not equal", url.getQuery(), URLQUERY);
        assertEquals("Reference string is not equal", url.getRef(), URLREFERENCE);
    }

    // Obtain the content from location
    @Test
    public void givenURL_whenOpenConnectionAndContentIsNotEmpty_thenSuccess() throws IOException {
        try {
            urlConnection = url.openConnection();
        } catch (IOException ex) {
            urlConnection = null;
            ex.printStackTrace();
        }
        assertNotNull("URL Connection is null", urlConnection);

        connection = null;
        assertTrue("URLConnection is not HttpURLConnection", urlConnection instanceof HttpURLConnection);
        if (urlConnection instanceof HttpURLConnection) {
            connection = (HttpURLConnection) urlConnection;
        }
        assertNotNull("Connection is null", connection);

        log.info(connection.getResponseCode() + " " + connection.getResponseMessage());

        try {
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (IOException ex) {
            in = null;
            ex.printStackTrace();
        }
        assertNotNull("Input stream failed", in);

        String current;
        try {
            while ((current = in.readLine()) != null) {
                urlContent += current;
            }
        } catch (IOException ex) {
            urlContent = null;
            ex.printStackTrace();
        }
        assertNotNull("Content is null", urlContent);
        assertTrue("Content is empty", urlContent.length() > 0);
    }
}
