package com.baeldung.cloud.openfeign.client;

import com.baeldung.cloud.openfeign.model.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "postClient", url = "${spring.cloud.openfeign.client.config.postClient.url}")
public interface PostClient {
    @GetMapping(value = "/{id}")
    Post getPostById(@PathVariable(value = "id") Integer id);
}
