package com.baeldung.okhttp;


import okhttp3.Response;

public interface ResponseDecoder {
    <T> T decode(Response response, Class<T> cls) throws DecodeException;
}
