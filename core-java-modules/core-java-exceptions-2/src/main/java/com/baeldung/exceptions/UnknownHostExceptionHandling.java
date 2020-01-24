package com.baeldung.exceptions;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

public class UnknownHostExceptionHandling {

    public static int getResponseCode(String hostname) throws IOException {
        URL url = new URL(hostname.trim());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        int resCode = -1;
        try {
            resCode = con.getResponseCode();
        } catch (UnknownHostException e){
            con.disconnect();
        }
        return resCode;
    }
    
    public static int getResponseCodeUnhandled(String hostname) throws IOException {
        URL url = new URL(hostname.trim());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        int resCode = con.getResponseCode();
        return resCode;
    }
}
