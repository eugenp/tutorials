package com.baeldung.networking.cookies;

import java.net.*;

public class ProxyAcceptCookiePolicy implements CookiePolicy {
    String acceptedProxy;

    public ProxyAcceptCookiePolicy(String acceptedProxy) {
        this.acceptedProxy = acceptedProxy;
    }

    public boolean shouldAccept(URI uri, HttpCookie cookie) {
        String host;
        try {
            host = InetAddress.getByName(uri.getHost()).getCanonicalHostName();
        } catch (UnknownHostException e) {
            host = uri.getHost();
        }

        if (!HttpCookie.domainMatches(acceptedProxy, host)) {
            return false;
        }

        return CookiePolicy.ACCEPT_ORIGINAL_SERVER.shouldAccept(uri, cookie);
    }
}
