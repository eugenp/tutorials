package com.baeldung.spring5webflux;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebFluxEventClient {
    private WebClient client;
    static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    static DecimalFormat nf = new DecimalFormat("##0.00");

    public WebFluxEventClient(String url) {
        client = WebClient.create(url);
    }

    public void run() {
        client.get()
                .uri("/locations")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .flatMapMany(r -> r.bodyToFlux(LocationEvent.class))
                .subscribe(event -> log.info(
                        String.format("timestamp: %s, latitude: %7s, longitude: %7s",
                                df.format(event.getTimestamp()),
                                nf.format(event.getPoint().getLatitude()),
                                nf.format(event.getPoint().getLongitude()))));
    }
}
