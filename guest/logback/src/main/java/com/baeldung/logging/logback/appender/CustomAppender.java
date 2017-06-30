package com.baeldung.logging.logback.appender;

import ch.qos.logback.core.AppenderBase;

public class CustomAppender<ILoggingEvent> extends AppenderBase<ILoggingEvent> {

    @Override
    public void start() {
        super.start();
        connectToYourPersistenceLayer();
    }

    private void connectToYourPersistenceLayer() {
        // acquire connection here
    }

    @Override
    protected void append(ILoggingEvent loggingEvent) {

        writeToPersistenceLayer(loggingEvent);

    }

    private void writeToPersistenceLayer(ILoggingEvent loggingEvent) {
        // persist log event here
    }

    @Override
    public void stop() {
        super.stop();
        stopPersistenceConnection();
    }

    private void stopPersistenceConnection() {
        // stopping connection here
    }
}
