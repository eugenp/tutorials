package com.baeldung.lambda.todo.api;

import feign.RequestLine;

public interface PostApi {
    @RequestLine("POST /posts")
    void makePost(PostItem item);
}
