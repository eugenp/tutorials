package com.baeldung.spring.cloud.bootstrap.gateway.client.rating;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "rating-service")
public interface RatingsClient {
    @RequestMapping(method = RequestMethod.GET, value="/ratings")
    List<Rating> getRatingsByBookId(@RequestParam("bookId") Long bookId, @RequestHeader("Cookie") String session);
}
