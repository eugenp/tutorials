package com.baeldung.cloud.netflix.feign.hystrix;

import com.baeldung.cloud.netflix.feign.client.JSONPlaceHolderClient;
import com.baeldung.cloud.netflix.feign.model.Post;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class JSONPlaceHolderFallback implements JSONPlaceHolderClient {

    @Override
    public List<Post> getPosts() {
        return Collections.emptyList();
    }

    @Override
    public Post getPostById(Long postId) {
        return null;
    }
}
