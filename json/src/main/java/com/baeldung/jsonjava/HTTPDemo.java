package com.baeldung.jsonjava;

import org.json.HTTP;
import org.json.JSONObject;

public class HTTPDemo {
    public static void main(String[] args) {
        System.out.println("10.1. Converting JSONObject to HTTP Header: ");
        jSONObjectToHTTPHeader();
        
        System.out.println("\n10.2. Converting HTTP Header String Back to JSONObject: ");
        hTTPHeaderToJSONObject();
    }
    
    public static void jSONObjectToHTTPHeader() {
        JSONObject jo = new JSONObject();
        jo.put("Method", "POST");
        jo.put("Request-URI", "http://www.example.com/");
        jo.put("HTTP-Version", "HTTP/1.1");
        System.out.println(HTTP.toString(jo));
    }
    
    public static void hTTPHeaderToJSONObject() {
        JSONObject obj = HTTP.toJSONObject("POST \"http://www.example.com/\" HTTP/1.1");
        System.out.println(obj);
    }
}
