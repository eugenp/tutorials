package com.sample.javacode;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class DisplayTime {

    public static void main(String[] args) {
        Date date = new java.util.Date();
        System.out.println(date);
        String gmt = date.toString();
        boolean flag = StringUtils.isEmpty(gmt);
        if (flag) {
            System.out.println("No time found");
        } else {
            System.out.println(gmt);
        }
    }

}
