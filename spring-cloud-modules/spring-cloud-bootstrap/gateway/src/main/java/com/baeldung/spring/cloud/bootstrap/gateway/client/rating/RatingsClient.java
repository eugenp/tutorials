package com.baeldung.spring.cloud.bootstrap.gateway.client.rating;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "rating-service")
public interface RatingsClient {

    @RequestMapping(value = "/ratings", method = { RequestMethod.GET })
    List<Rating> getRatingsByBookId(@RequestParam("bookId") Long bookId, @RequestHeader("Cookie") String session);
}
