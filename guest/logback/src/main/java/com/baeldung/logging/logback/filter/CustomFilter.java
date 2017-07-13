package com.baeldung.logging.logback.filter;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class CustomFilter extends Filter<ILoggingEvent> {

    @Override
    public FilterReply decide(ILoggingEvent iLoggingEvent) {

        if (iLoggingEvent.getFormattedMessage().contains("important")) {
            return FilterReply.ACCEPT;
        } else if(iLoggingEvent.getLevel() == Level.ERROR) {
            return FilterReply.NEUTRAL;
        }
        return FilterReply.DENY;
    }
}
