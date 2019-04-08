package com.baeldung.string.searching;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * BAEL-2832
 */
public class SubstringSearch {

    public static void main(String args[]) {
        new SubstringSearch().searchUsingPattern();
    }

    public void searchSubstringWithContains() {
        // contains will return true
        "Hey Ho, let's go".contains("Hey");

        // contains will return false, because it's case sensitive
        "Hey Ho, let's go".contains("hey");

        // contains will return true, because it's all lowercase
        "Hey Ho, let's go".toLowerCase().contains("hey");

        // contains will return false, because 'jey' can't be found
        "Hey Ho, let's go".contains("jey");
    }

    public void searchSubstringWithIndexOf() {
        // indexOf will return 9
        "Bohemian Rhapsodyan".indexOf("Rhap");

        // indexOf will return -1, because it's case sensitive
        "Bohemian Rhapsodyan".indexOf("rhap");

        // indexOf will return 9, because it's all lowercase
        "Bohemian Rhapsodyan".toLowerCase().indexOf("rhap");

        // it will return 6, because it's the first ocurrence. Sorry Queen for being blasphemic
        "Bohemian Rhapsodyan".indexOf("an");
    }

    public void searchSubstringWithStringUtils() {

        // it will return true
        StringUtils.containsIgnoreCase("Runaway train ", "train");

        // it will also return true, because ignores case ;)
        StringUtils.containsIgnoreCase("Runaway train", "Train");

    }

    public void searchUsingPattern() {
        
        //We create the Pattern first
        Pattern pattern = Pattern.compile("(?<!\\S)" + "road" + "(?!\\S)");
        
        // We need to create the Matcher after   
        Matcher matcher = pattern.matcher("Hit the road Jack");
        
        // find will return true when the first match is found
        matcher.find(); 
        
        // We will create a different matcher with a diferent text   
        matcher = pattern.matcher("and don't you come back no more");
        
        // find will return false, because 'road' can't be find as a substring
        matcher.find();
    }  
}