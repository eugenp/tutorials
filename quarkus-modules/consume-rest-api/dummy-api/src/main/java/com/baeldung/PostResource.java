package com.baeldung;

import java.util.Arrays;
import java.util.List;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/posts")
public class PostResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Post> getPosts() {
        return Arrays.asList(new Post(1L, "Post One", "This is the first post"), new Post(2L, "Post Two", "This is the second post"));
    }
}
