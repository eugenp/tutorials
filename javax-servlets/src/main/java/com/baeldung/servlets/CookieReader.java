package com.baeldung.servlets;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Created by adam.
 *
 * Class which simplifies reading cookies from request.
 */
public class CookieReader {

    private HttpServletRequest request;

    /**
     * The constructor.
     *
     * @param request request from which cookies will be read
     */
    public CookieReader(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * Reads cookie by key from request.
     *
     * @param key the key of a cookie
     * @return returns cookie value (or null if cookie with given key does not exist)
     */
    public String readCookie(String key) {
        return Arrays.stream(request.getCookies())
                 .filter(c -> key.equals(c.getName()))
                 .map(Cookie::getValue)
                 .findFirst()
                 .orElse(null);
    }

}
