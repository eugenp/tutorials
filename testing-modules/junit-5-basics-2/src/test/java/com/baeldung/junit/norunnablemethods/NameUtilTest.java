package com.baeldung.junit.norunnablemethods;

/**
 * This class is actually an utility/helper class, 
 * but incorrectly named so that it could be confused with a real Test class
 * 
 * @see @NameutilTestHelper
 */
public class NameUtilTest {

    public String formatName(String name) {
        return (name == null) ? name : name.replace("$", "_");
    }
}
