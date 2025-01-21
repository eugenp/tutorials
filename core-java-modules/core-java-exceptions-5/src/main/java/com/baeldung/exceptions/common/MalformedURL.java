package com.baeldung.exceptions.common;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MalformedURL {

    private static Logger LOGGER = LoggerFactory.getLogger(MalformedURL.class);

    public static void main(String[] args) {

        URL baeldungURL = null;

        try {
            baeldungURL = new URI("malformedurl").toURL();
        } catch (MalformedURLException | URISyntaxException e) {
            LOGGER.error("MalformedURLException caught!");
        }

    }

}
