package com.baeldung.networking.uriurl;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class URIDemo {
    private final Logger logger = LoggerFactory.getLogger(URIDemo.class);

    String URISTRING = "https://wordpress.org:443/support/topic/page-jumps-within-wordpress/?replies=3#post-2278484";
    // parsed locator
    String URISCHEME = "https";
    String URISCHEMESPECIFIC;
    String URIHOST = "wordpress.org";
    String URIAUTHORITY = "wordpress.org:443";

    String URIPATH = "/support/topic/page-jumps-within-wordpress/";
    int URIPORT = 443;
    String URIQUERY = "replies=3";
    String URIFRAGMENT = "post-2278484";
    String URIUSERINFO;
    String URICOMPOUND = URISCHEME + "://" + URIHOST + ":" + URIPORT + URIPATH + "?" + URIQUERY + "#" + URIFRAGMENT;

    URI uri;
    URL url;
    BufferedReader in = null;
    String URIContent = "";

    private String getParsedPieces(URI uri) {
        logger.info("*** List of parsed pieces ***");
        URISCHEME = uri.getScheme();
        logger.info("URISCHEME: " + URISCHEME);
        URISCHEMESPECIFIC = uri.getSchemeSpecificPart();
        logger.info("URISCHEMESPECIFIC: " + URISCHEMESPECIFIC);
        URIHOST = uri.getHost();
        URIAUTHORITY = uri.getAuthority();
        logger.info("URIAUTHORITY: " + URIAUTHORITY);
        logger.info("URIHOST: " + URIHOST);
        URIPATH = uri.getPath();
        logger.info("URIPATH: " + URIPATH);
        URIPORT = uri.getPort();
        logger.info("URIPORT: " + URIPORT);
        URIQUERY = uri.getQuery();
        logger.info("URIQUERY: " + URIQUERY);
        URIFRAGMENT = uri.getFragment();
        logger.info("URIFRAGMENT: " + URIFRAGMENT);

        try {
            url = uri.toURL();
        } catch (MalformedURLException e) {
            logger.info("MalformedURLException thrown: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            logger.info("IllegalArgumentException thrown: " + e.getMessage());
            e.printStackTrace();
        }
        return url.toString();
    }

    public String testURIAsNew(String URIString) {
        // creating URI object
        try {
            uri = new URI(URIString);
        } catch (URISyntaxException e) {
            logger.info("URISyntaxException thrown: " + e.getMessage());
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
        return getParsedPieces(uri);
    }

    public String testURIAsCreate(String URIString) {
        // creating URI object
        uri = URI.create(URIString);
        return getParsedPieces(uri);
    }

    public static void main(String[] args) throws Exception {
        URIDemo demo = new URIDemo();
        String contentCreate = demo.testURIAsCreate(demo.URICOMPOUND);
        demo.logger.info(contentCreate);
        String contentNew = demo.testURIAsNew(demo.URICOMPOUND);
        demo.logger.info(contentNew);
    }
}
