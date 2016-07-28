package com.baeldung.java9.httpclient;

public class Main {

    public static void main(String[] args) throws Exception {
        SimpleHttpRequests shr = new SimpleHttpRequests();
        shr.quickGet();
        shr.PostMehtod();

        shr.configureHttpClient();
        shr.asyncGet();
    }

}
