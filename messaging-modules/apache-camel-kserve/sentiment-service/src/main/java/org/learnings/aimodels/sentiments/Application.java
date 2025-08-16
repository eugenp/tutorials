package org.learnings.aimodels.sentiments;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.learnings.aimodels.sentiments.web.api.SentimentsRoute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new SentimentsRoute());

        context.start();
        log.info("🚀 Sentiment service running on http://localhost:8080/sentiments");
        Thread.sleep(Long.MAX_VALUE);
        context.stop();
    }
}
