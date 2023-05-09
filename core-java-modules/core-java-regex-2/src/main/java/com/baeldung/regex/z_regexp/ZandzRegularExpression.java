package com.baeldung.regex.z_regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZandzRegularExpression {
    public void CapitalCase(String Input_,String Pattern_){
        Pattern pattern  = Pattern.compile(Pattern_+"\\Z");
        Matcher matcher  = pattern.matcher(Input_);
        if (matcher.find()) {
            System.out.println("Match found!");
        } else {
            System.out.println("Match not found.");
        }
    }
    public void SmallCase(String Input_,String Pattern_){
        Pattern pattern = Pattern.compile(Pattern_+"\\z");
        Matcher matcher = pattern.matcher(Input_);
        if (matcher.find()) {
            System.out.println("Match found!");
        } else {
            System.out.println("Match not found.");
        }
    }
}
