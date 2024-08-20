package com.baeldung.javahttpclient;

import java.util.List;

import com.baeldung.Post;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/java-http-client/consume-posts")
public class JavaHttpClientPostResource {

    @Inject
    JavaHttpClientPostService postService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Post> getPosts() {
        return postService.getPosts();
    }
}
