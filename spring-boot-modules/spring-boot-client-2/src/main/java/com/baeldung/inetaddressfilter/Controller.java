package com.baeldung.inetaddressfilter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class Controller {

    private final RestClient restClient;

    public Controller(RestClient restClient) {
        this.restClient = restClient;
    }

    @GetMapping("/fetch")
    public String fetch(@RequestParam String url) {
        return restClient.get()
          .uri(url)
          .retrieve()
          .body(String.class);
    }
}
