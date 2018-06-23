package com.baeldung.reactive.client.runner;

import com.baeldung.reactive.client.SmsAlertReactiveClientApplication;
import com.baeldung.reactive.events.SmsEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SmsAlertClientRunner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsAlertReactiveClientApplication.class);

    private WebClient smsAlertWebClient;

    public SmsAlertClientRunner() {
        smsAlertWebClient =  WebClient.create("http://localhost:8080/api");
    }

    public void setSmsAlertWebClient(WebClient smsAlertWebClient) {
        this.smsAlertWebClient = smsAlertWebClient;
    }

    @Override
    public void run(String... args) throws Exception {
        smsAlertWebClient.get()
                .uri("/short-messages/774222382")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .flatMapMany(clientResponse -> clientResponse.bodyToFlux(SmsEvent.class))
                .subscribe(events -> LOGGER.info("Received :: {}", events),
                        err -> LOGGER.error("Error on Stream :: ", err),
                        () -> LOGGER.info("Client stopped..."));
    }
}
