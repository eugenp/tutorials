package com.baeldung.blade.sample;

import com.blade.mvc.annotation.Path;
import com.blade.mvc.annotation.Route;
import com.blade.mvc.http.Response;

@Path
public class LogExampleController {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LogExampleController.class);

    @Route(value = "/test-logs")
    public void testLogs(Response response) {
        log.trace("This is a TRACE Message");
        log.debug("This is a DEBUG Message");
        log.info("This is an INFO Message");
        log.warn("This is a WARN Message");
        log.error("This is an ERROR Message");
        response.text("Check in ./logs");
    }

}
