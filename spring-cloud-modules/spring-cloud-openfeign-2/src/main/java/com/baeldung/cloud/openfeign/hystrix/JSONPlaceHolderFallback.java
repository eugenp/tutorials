package com.baeldung.cloud.openfeign.hystrix;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.baeldung.cloud.openfeign.client.JSONPlaceHolderClient;
import com.baeldung.cloud.openfeign.model.Post;

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
