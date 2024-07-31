package com.baeldung.customurlconnection;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

public class CustomURLStreamHandlerFactory implements URLStreamHandlerFactory {

    @Override
    public URLStreamHandler createURLStreamHandler(String protocol) {
        // Check if the requested protocol is our custom protocol
        if ("myprotocol".equals(protocol)) {
            return new CustomURLStreamHandler();
        }
        return null; // Let the default handler deal with other protocols
    }
}
