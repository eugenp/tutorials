package com.baeldung.forwardheaders;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class HealthController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HealthController.class);

    @GetMapping("/health")
    public ResponseEntity<Void> getHealth(HttpServletRequest request) {
        LOGGER.info("""
                scheme : {}
                Remote Host IP : {}
                Remote Port : {}
                Server Name : {}
                Server Port : {}
                Request URL : {}""", request.getScheme(), request.getRemoteHost(), request.getRemotePort(), request.getServerName(), request.getServerPort(),
            ServletUriComponentsBuilder.fromRequest(request)
                .build()
                .toUriString());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
