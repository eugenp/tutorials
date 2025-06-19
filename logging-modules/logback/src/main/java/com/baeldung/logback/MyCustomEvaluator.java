package com.baeldung.logback;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.boolex.EventEvaluatorBase;

public class MyCustomEvaluator extends EventEvaluatorBase<ILoggingEvent> {

    @Override
    public boolean evaluate(ILoggingEvent event) {
        String message = event.getMessage();
        return message != null && message.contains("billing");
    }
}
