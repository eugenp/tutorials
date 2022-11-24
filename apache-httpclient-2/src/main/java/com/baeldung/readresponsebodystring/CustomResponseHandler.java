package com.baeldung.readresponsebodystring;

import java.io.IOException;

import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;

public class CustomResponseHandler implements HttpClientResponseHandler<String> {
    @Override
    public String handleResponse(ClassicHttpResponse response) throws HttpException, IOException {
        final HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity);
    }
}
