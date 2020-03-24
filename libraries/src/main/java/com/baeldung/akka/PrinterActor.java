package com.baeldung.akka;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class PrinterActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    public static Props props(String text) {
        return Props.create(PrinterActor.class, text);
    }

    public static final class PrintFinalResult {
        Integer totalNumberOfWords;

        public PrintFinalResult(Integer totalNumberOfWords) {
            this.totalNumberOfWords = totalNumberOfWords;
        }
    }

    @Override
    public void preStart() {
        log.info("Starting PrinterActor {}", this);
    }

    @Override
    public void postStop() {
        log.info("Stopping PrinterActor {}", this);
    }


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(PrinterActor.PrintFinalResult.class,
                        r -> {
                            log.info("Received PrintFinalResult message from " + getSender());
                            log.info("The text has a total number of {} words", r.totalNumberOfWords);
                        })
                .build();
    }
}
