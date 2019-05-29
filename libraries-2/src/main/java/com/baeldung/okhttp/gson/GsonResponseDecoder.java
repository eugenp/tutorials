package com.baeldung.okhttp.gson;

import com.baeldung.okhttp.DecodeException;
import com.baeldung.okhttp.ResponseDecoder;
import com.google.gson.Gson;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.InputStreamReader;

public class GsonResponseDecoder implements ResponseDecoder {

    private Gson gson;

    public GsonResponseDecoder(Gson gson) {
        this.gson = gson;
    }

    @Override
    public <T> T decode(Response response, Class<T> cls) throws DecodeException {
        try {
            ResponseBody responseBody = response.body();
            if (responseBody != null && responseBody.contentLength() != 0 ) {
                return gson.fromJson(new InputStreamReader(responseBody.byteStream()), cls);
            }
            throw new DecodeException("Empty Response");
        } catch (DecodeException e) {
            throw e;
        } catch (Exception e) {
            throw new DecodeException(e);
        }
    }

}
