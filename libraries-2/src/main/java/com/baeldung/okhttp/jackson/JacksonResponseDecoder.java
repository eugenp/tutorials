package com.baeldung.okhttp.jackson;

import com.baeldung.okhttp.DecodeException;
import com.baeldung.okhttp.ResponseDecoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class JacksonResponseDecoder implements ResponseDecoder {

    private ObjectMapper objectMapper;

    public JacksonResponseDecoder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> T decode(Response response, Class<T> cls) throws DecodeException {
        try {
            ResponseBody responseBody = response.body();
            if (responseBody != null && responseBody.contentLength() != 0 ) {
                return  objectMapper.readValue(responseBody.byteStream(), cls);
            }
            throw new DecodeException("Empty Response");
        } catch (DecodeException e) {
            throw e;
        } catch (Exception e) {
            throw new DecodeException(e);
        }
    }
}
