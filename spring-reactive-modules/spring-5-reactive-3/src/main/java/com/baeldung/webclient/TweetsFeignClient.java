package com.baeldung.webclient;

import java.net.URI;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "tweetsBlocking", url = "http://localhost:8080")
public interface TweetsFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/slow-service-tweets", produces = "application/json")
    List<Tweet> getTweetsBlocking(URI baseUrl);
}
