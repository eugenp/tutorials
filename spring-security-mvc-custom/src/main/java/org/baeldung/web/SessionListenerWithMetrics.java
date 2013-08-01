package org.baeldung.web;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListenerWithMetrics implements HttpSessionListener {

    private static int totalActiveSessions;

    public SessionListenerWithMetrics() {
        super();
    }

    // API

    public static int getTotalActiveSession() {
        return totalActiveSessions;
    }

    @Override
    public void sessionCreated(final HttpSessionEvent arg0) {
        totalActiveSessions++;
        System.out.println("sessionCreated - add one session into counter");
    }

    @Override
    public void sessionDestroyed(final HttpSessionEvent arg0) {
        totalActiveSessions--;
        System.out.println("sessionDestroyed - deduct one session from counter");
    }

}