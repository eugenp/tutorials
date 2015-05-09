package org.baeldung.config;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.baeldung.reddit.util.MyFeatures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionListener implements HttpSessionListener {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        logger.info("==== Session is created ====");
        event.getSession().setMaxInactiveInterval(60 * 60);
        event.getSession().setAttribute("PREDICTION_FEATURE", MyFeatures.PREDICTION_FEATURE);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        logger.info("==== Session is destroyed ====");
    }
}