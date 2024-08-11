package com.baeldung.junit.norunnablemethods;

/**
 * This class is actually a utility/helper class, but it is incorrectly named
 * ending in Test, that can be misinterpreted as a real Test class
 * 
 * @see @NameUtilTestHelper with a name that avoids this problem
 */
public class NameUtilTest {

    public String formatName(String name) {
        return (name == null) ? name : name.replace("$", "_");
    }
}
