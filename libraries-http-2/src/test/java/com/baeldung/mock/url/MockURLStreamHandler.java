package com.baeldung.mock.url;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class MockURLStreamHandler extends URLStreamHandler {

    private MockHttpURLConnection mockHttpURLConnection;

    public MockURLStreamHandler(MockHttpURLConnection mockHttpURLConnection) {
        this.mockHttpURLConnection = mockHttpURLConnection;
    }

    @Override
    protected URLConnection openConnection(URL url) throws IOException {
        return this.mockHttpURLConnection;
    }

}
