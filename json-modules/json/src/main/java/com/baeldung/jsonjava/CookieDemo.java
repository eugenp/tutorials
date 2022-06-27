package com.baeldung.jsonjava;

import org.json.Cookie;
import org.json.JSONObject;

public class CookieDemo {
    public static void main(String[] args) {
        System.out.println("9.1. Converting a Cookie String into a JSONObject");
        cookieStringToJSONObject();
        
        System.out.println("\n9.2. Converting a JSONObject into Cookie String");
        jSONObjectToCookieString();
    }
    
    public static void cookieStringToJSONObject() {
        String cookie = "username=John Doe; expires=Thu, 18 Dec 2013 12:00:00 UTC; path=/";
        JSONObject cookieJO = Cookie.toJSONObject(cookie);
        System.out.println(cookieJO);
    }
    
    public static void jSONObjectToCookieString() {
        JSONObject cookieJO = new JSONObject();
        cookieJO.put("name", "username");
        cookieJO.put("value", "John Doe");
        cookieJO.put("expires", "Thu, 18 Dec 2013 12:00:00 UTC");
        cookieJO.put("path", "/");
        String cookie = Cookie.toString(cookieJO);
        System.out.println(cookie);
    }
}
