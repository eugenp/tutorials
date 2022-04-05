package com.baeldung.okhttp.events;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.Call;
import okhttp3.EventListener;
import okhttp3.Request;
import okhttp3.Response;

public class SimpleLogEventsListener extends EventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleLogEventsListener.class);

    @Override
    public void callStart(Call call) {
        LOGGER.info("callStart at {}", LocalDateTime.now());
    }

    @Override
    public void requestHeadersEnd(Call call, Request request) {
        LOGGER.info("requestHeadersEnd at {} with headers {}", LocalDateTime.now(), request.headers());
    }

    @Override
    public void responseHeadersEnd(Call call, Response response) {
        LOGGER.info("responseHeadersEnd at {} with headers {}", LocalDateTime.now(), response.headers());
    }

    @Override
    public void callEnd(Call call) {
        LOGGER.info("callEnd at {}", LocalDateTime.now());
    }

}
