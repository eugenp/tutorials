package com.baeldung.feign.retry;

import com.baeldung.feign.clients.BookClient;
import feign.Feign;
import feign.Logger;
import feign.Retryer;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

@Getter
public class ResilientFeignClientBuilder {
    public BookClient bookClient = createClient(BookClient.class, "http://localhost:8081/api/books");

    public static <T> T createClient(Class<T> type, String uri) {
        return Feign.builder()
            .client(new OkHttpClient())
            .encoder(new GsonEncoder())
            .decoder(new GsonDecoder())
            .retryer(new Retryer.Default(100L, TimeUnit.SECONDS.toMillis(3L), 5))
            .errorDecoder(new Custom5xxErrorDecoder())
            .target(type, uri);
    }
}
