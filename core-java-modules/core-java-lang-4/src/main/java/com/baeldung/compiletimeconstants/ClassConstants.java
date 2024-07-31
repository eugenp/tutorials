package com.baeldung.compiletimeconstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class ClassConstants {

    public static final int MAXIMUM_NUMBER_OF_USERS = 10;
    public static final String DEFAULT_USERNAME = "unknown";

    public static final Logger log = LoggerFactory.getLogger(ClassConstants.class);
    public static final List<String> contributorGroups = Arrays.asList("contributor", "author");

    public static final int MAXIMUM_NUMBER_OF_GUESTS = MAXIMUM_NUMBER_OF_USERS * 10;

}