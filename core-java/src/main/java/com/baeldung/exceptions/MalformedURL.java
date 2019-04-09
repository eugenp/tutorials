package com.baeldung.exceptions;

import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MalformedURL {

    private static Logger LOGGER = LoggerFactory.getLogger(MalformedURL.class);

    public static void main(String[] args) {

        URL baeldungURL = null;

        try {
            baeldungURL = new URL("malformedurl");
        } catch (MalformedURLException e) {
            LOGGER.error("MalformedURLException caught!");
        }

    }

}
