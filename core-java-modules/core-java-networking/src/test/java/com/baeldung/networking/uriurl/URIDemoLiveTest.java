package com.baeldung.networking.uriurl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.networking.uriurl.URLDemo;

@FixMethodOrder
public class URIDemoLiveTest {
    private final Logger log = LoggerFactory.getLogger(URIDemoLiveTest.class);
    String URISTRING = "https://wordpress.org:443/support/topic/page-jumps-within-wordpress/?replies=3#post-2278484";
    // parsed locator
    static String URISCHEME = "https";
    String URISCHEMESPECIFIC;
    static String URIHOST = "wordpress.org";
    static String URIAUTHORITY = "wordpress.org:443";

    static String URIPATH = "/support/topic/page-jumps-within-wordpress/";
    int URIPORT = 443;
    static int URIDEFAULTPORT = 443;
    static String URIQUERY = "replies=3";
    static String URIFRAGMENT = "post-2278484";
    static String URICOMPOUND = URISCHEME + "://" + URIHOST + ":" + URIDEFAULTPORT + URIPATH + "?" + URIQUERY + "#" + URIFRAGMENT;

    static URI uri;
    URL url;
    BufferedReader in = null;
    String URIContent = "";

    @BeforeClass
    public static void givenEmplyURL_whenInitializeURL_thenSuccess() throws URISyntaxException {
        uri = new URI(URICOMPOUND);
    }

    // check parsed URL
    @Test
    public void givenURI_whenURIIsParsed_thenSuccess() {
        assertNotNull("URI is null", uri);
        assertEquals("URI string is not equal", uri.toString(), URISTRING);
        assertEquals("Scheme is not equal", uri.getScheme(), URISCHEME);
        assertEquals("Authority is not equal", uri.getAuthority(), URIAUTHORITY);
        assertEquals("Host string is not equal", uri.getHost(), URIHOST);
        assertEquals("Path string is not equal", uri.getPath(), URIPATH);
        assertEquals("Port number is not equal", uri.getPort(), URIPORT);
        assertEquals("Query string is not equal", uri.getQuery(), URIQUERY);
        assertEquals("Fragment string is not equal", uri.getFragment(), URIFRAGMENT);
    }
}
