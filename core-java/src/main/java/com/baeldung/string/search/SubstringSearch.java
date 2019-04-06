package com.baeldung.string.search;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        org.apache.commons.lang.StringUtils.containsIgnoreCase("Runaway train ", "train");

        // it will also return true, because ignores case ;)
        org.apache.commons.lang.StringUtils.containsIgnoreCase("Runaway train", "Train");

    }

    public void searchUsingPattern() {
        
        //We create the Pattern first
        Pattern pattern = Pattern.compile("e*w");
        
        // We need to create the Matcher after   
        Matcher matcher = pattern.matcher("eew");
        // This will return true
        matcher.matches();
        
        // This will also return True, and creates the matcher before comparing
        pattern.matcher("eeeeeeeew").matches();
        
        // This will return false, because "a" is not present in the RegEx expression
        pattern.matcher("aew").matches();
    }
    
    public void searchUsingMatchesMethod() {
        
        // Will return true. Simple, isn't it?
        "eew".matches("e*w");
        
        // Will return false
        "kw".matches("e*w");
    }   
}
