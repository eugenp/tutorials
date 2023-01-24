package com.baeldung.jsonjava;

import org.json.JSONTokener;

public class JSONTokenerDemo {
    public static void main(String[] args) {
        JSONTokener jt = new JSONTokener("Sample String");
         
        while(jt.more()) {
            System.out.println(jt.next());
        }
    }
}
