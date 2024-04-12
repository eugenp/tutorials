package com.baeldung.maven.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Simple class to demonstrate the importance of Maven encoding when dealing with NonAscii characters
 */
public class NonAsciiString {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(NonAsciiString.class);
    
    public static String getNonAsciiString() {
        
        String nonAsciiStr = "ÜÝÞßàæç";
        LOGGER.info(nonAsciiStr);
        return nonAsciiStr;
        
        /*We can even use non-ASCII characters as Java variables names.
        The below will run fine when built using Maven UTF-8 encoding,
        but not when using US-ASCII. Give it a go!*/
        /*String nonAsciiŞŧř = "ÜÝÞßàæç";
        LOGGER.info(nonAsciiŞŧř);
        return nonAsciiŞŧř;*/
    }
}
