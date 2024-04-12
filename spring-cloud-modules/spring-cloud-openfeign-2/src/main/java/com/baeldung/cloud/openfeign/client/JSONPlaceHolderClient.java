package com.baeldung.cloud.openfeign.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baeldung.cloud.openfeign.config.ClientConfiguration;
import com.baeldung.cloud.openfeign.hystrix.JSONPlaceHolderFallback;
import com.baeldung.cloud.openfeign.model.Post;

@FeignClient(value = "jplaceholder",
        url = "https://jsonplaceholder.typicode.com/",
        configuration = ClientConfiguration.class,
        fallback = JSONPlaceHolderFallback.class)
public interface JSONPlaceHolderClient {

    @RequestMapping(method = RequestMethod.GET, value = "/posts")
    List<Post> getPosts();


    @RequestMapping(method = RequestMethod.GET, value = "/posts/{postId}", produces = "application/json")
    Post getPostById(@PathVariable("postId") Long postId);
}
