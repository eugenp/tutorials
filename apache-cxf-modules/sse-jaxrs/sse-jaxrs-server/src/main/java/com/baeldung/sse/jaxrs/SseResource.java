package com.baeldung.sse.jaxrs;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.sse.OutboundSseEvent;
import jakarta.ws.rs.sse.Sse;
import jakarta.ws.rs.sse.SseBroadcaster;
import jakarta.ws.rs.sse.SseEventSink;

@ApplicationScoped
@Path("stock")
public class SseResource {

    @Inject
    private StockService stockService;

    private Sse sse;
    private SseBroadcaster sseBroadcaster;
    private OutboundSseEvent.Builder eventBuilder;

    @Context
    public void setSse(Sse sse) {
        this.sse = sse;
        this.eventBuilder = sse.newEventBuilder();
        this.sseBroadcaster = sse.newBroadcaster();
    }

    @GET
    @Path("prices")
    @Produces("text/event-stream")
    public void getStockPrices(@Context SseEventSink sseEventSink,
                               @HeaderParam(HttpHeaders.LAST_EVENT_ID_HEADER) @DefaultValue("-1") int lastReceivedId) {

        int lastEventId = 1;
        if (lastReceivedId != -1) {
            lastEventId = ++lastReceivedId;
        }
        boolean running = true;
        while (running) {
            Stock stock = stockService.getNextTransaction(lastEventId);
            if (stock != null) {
                OutboundSseEvent sseEvent = this.eventBuilder
                        .name("stock")
                        .id(String.valueOf(lastEventId))
                        .mediaType(MediaType.APPLICATION_JSON_TYPE)
                        .data(Stock.class, stock)
                        .reconnectDelay(3000)
                        .comment("price change")
                        .build();
                sseEventSink.send(sseEvent);
                lastEventId++;
            }
            //Simulate connection close
            if (lastEventId % 5 == 0) {
                sseEventSink.close();
                break;
            }

            try {
                //Wait 5 seconds
                Thread.sleep(5 * 1000);
            } catch (InterruptedException ex) {
                // ...
            }
            //Simulatae a while boucle break
            running = lastEventId <= 2000;
        }
        sseEventSink.close();
    }

    @GET
    @Path("subscribe")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void listen(@Context SseEventSink sseEventSink) {
        sseEventSink.send(sse.newEvent("Welcome !"));
        this.sseBroadcaster.register(sseEventSink);
        sseEventSink.send(sse.newEvent("You are registred !"));
    }

    @GET
    @Path("publish")
    public void broadcast() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                int lastEventId = 0;
                boolean running = true;
                while (running) {
                    lastEventId++;
                    Stock stock = stockService.getNextTransaction(lastEventId);
                    if (stock != null) {
                        OutboundSseEvent sseEvent = eventBuilder
                                .name("stock")
                                .id(String.valueOf(lastEventId))
                                .mediaType(MediaType.APPLICATION_JSON_TYPE)
                                .data(Stock.class, stock)
                                .reconnectDelay(3000)
                                .comment("price change")
                                .build();
                        sseBroadcaster.broadcast(sseEvent);
                    }
                    try {
                        //Wait 5 seconds
                        Thread.currentThread().sleep(5 * 1000);
                    } catch (InterruptedException ex) {
                        // ...
                    }
                    //Simulatae a while boucle break
                    running = lastEventId <= 2000;
                }
            }
        };
        new Thread(r).start();
    }
}
