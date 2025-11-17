package com.baeldung.cloud.openfeign.getbody;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "sampleClient", url = "http://localhost:8080")
public interface GetBodyFeignClient {

    @GetMapping("/api/search")
    String search(@RequestBody SearchRequest searchRequest);

    @GetMapping("/api/search")
    String searchWithSpringQueryMap(@SpringQueryMap SearchRequest searchRequest);
}