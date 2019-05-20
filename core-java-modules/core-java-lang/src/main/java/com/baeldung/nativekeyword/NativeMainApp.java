package com.baeldung.nativekeyword;

import com.baeldung.nativekeyword.DateTimeUtils;

public class NativeMainApp {
    public static void main(String[] args) {
        DateTimeUtils dateTimeUtils = new DateTimeUtils();
        String input = dateTimeUtils.getSystemTime();
        System.out.println("System time is : " + input);
    }
}
