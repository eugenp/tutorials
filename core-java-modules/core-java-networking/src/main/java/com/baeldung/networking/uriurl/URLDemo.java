package com.baeldung.networking.uriurl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class URLDemo {
    private final Logger log = LoggerFactory.getLogger(URLDemo.class);

    String URLSTRING = "https://wordpress.org:443/support/topic/page-jumps-within-wordpress/?replies=3#post-2278484";
    // parsed locator
    String URLPROTOCOL = "https";
    // final static String URLAUTHORITY = "wordpress.org:443";
    String URLHOST = "wordpress.org";
    String URLPATH = "/support/topic/page-jumps-within-wordpress/";
    // final static String URLFILENAME = "/support/topic/page-jumps-within-wordpress/?replies=3";
    // final static int URLPORT = 443;
    int URLDEFAULTPORT = 443;
    String URLQUERY = "replies=3";
    String URLREFERENCE = "post-2278484";
    String URLCOMPOUND = URLPROTOCOL + "://" + URLHOST + ":" + URLDEFAULTPORT + URLPATH + "?" + URLQUERY + "#" + URLREFERENCE;

    URL url;
    URLConnection urlConnection = null;
    HttpURLConnection connection = null;
    BufferedReader in = null;
    String urlContent = "";

    public String testURL(String urlString) throws IOException, IllegalArgumentException {
        String urlStringCont = "";
        // comment the if clause if experiment with URL
        /*if (!URLSTRING.equals(urlString)) {
            throw new IllegalArgumentException("URL String argument is not proper: " + urlString);
        }*/
        // creating URL object
        url = new URL(urlString);
        // get URL connection
        urlConnection = url.openConnection();
        connection = null;
        // we can check, if connection is proper type
        if (urlConnection instanceof HttpURLConnection) {
            connection = (HttpURLConnection) urlConnection;
        } else {
            log.info("Please enter an HTTP URL");
            throw new IOException("HTTP URL is not correct");
        }
        // we can check response code (200 OK is expected)
        log.info(connection.getResponseCode() + " " + connection.getResponseMessage());
        in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String current;

        while ((current = in.readLine()) != null) {
            urlStringCont += current;
        }
        return urlStringCont;
    }

    public static void main(String[] args) throws Exception {
        URLDemo demo = new URLDemo();
        String content = demo.testURL(demo.URLCOMPOUND);
        demo.log.info(content);
    }
}
