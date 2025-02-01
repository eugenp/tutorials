package com.baeldung.deletewrequestbody.client.helper;

import java.net.URI;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

public class HttpDeleteBody extends HttpEntityEnclosingRequestBase {

    public HttpDeleteBody(final String uri) {
        super();
        setURI(URI.create(uri));
    }

    @Override
    public String getMethod() {
        return "DELETE";
    }
}
