package com.baeldung.sse.jaxrs.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.sse.InboundSseEvent;
import javax.ws.rs.sse.SseEventSource;
import java.util.function.Consumer;

public class SseClientApp {

    private static final String url = "http://127.0.0.1:9080/sse-jaxrs-server/sse/stock/prices";

    public static void main(String... args) throws Exception {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        try (SseEventSource eventSource = SseEventSource.target(target).build()) {

            eventSource.register(onEvent, onError, onComplete);
            eventSource.open();

            //Consuming events for one hour
            Thread.sleep(60 * 60 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.close();
        System.out.println("End");
    }

    // A new event is received
    private static Consumer<InboundSseEvent> onEvent = (inboundSseEvent) -> {
        String data = inboundSseEvent.readData();
        System.out.println(data);
    };

    //Error
    private static Consumer<Throwable> onError = (throwable) -> {
        throwable.printStackTrace();
    };

    //Connection close and there is nothing to receive
    private static Runnable onComplete = () -> {
        System.out.println("Done!");
    };

}
