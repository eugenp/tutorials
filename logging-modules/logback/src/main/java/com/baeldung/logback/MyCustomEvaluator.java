package com.baeldung.logback;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.boolex.EventEvaluatorBase;
import ch.qos.logback.core.boolex.EvaluationException;

public class BillingMessageEvaluator extends EventEvaluatorBase<ILoggingEvent> {

    @Override
    public boolean evaluate(ILoggingEvent event) throws NullPointerException, EvaluationException {
        String message = event.getMessage();
        return message != null && message.contains("billing");
    }
}
