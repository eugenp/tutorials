package com.baeldung.spring.rest.compress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MessageController {

    protected static final String PROCESSED = "Processed ";

    protected static final String REQUEST_MAPPING = "process";

    private static final Logger LOG = LoggerFactory.getLogger(MessageController.class);

    /**
     * A simple endpoint that responds with "Processed " + supplied Message content.
     *
     * @param headers
     * @param message
     * @return
     */
    @PostMapping(value = REQUEST_MAPPING)
    public ResponseEntity<String> processMessage(@RequestHeader Map<String, String> headers,
            @RequestBody Message message) {

        // Print headers
        headers.forEach((k, v) -> LOG.info(k + "=" + v));

        return ResponseEntity.ok(PROCESSED + message.getText());
    }
}
