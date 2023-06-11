package com.baeldung.limitrequests.server;

import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/random")
public class RandomController {

    public static final String CLIENT_ID_HEADER = "client-id";
    private static final Random RANDOM = new Random();

    @GetMapping
    Integer getRandom(@RequestHeader(CLIENT_ID_HEADER) String clientId) throws InterruptedException {
        return Concurrency.protect(clientId, () -> RANDOM.nextInt(50));
    }
}
