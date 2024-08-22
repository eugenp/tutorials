package com.baeldung.restclient;

import java.util.List;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.baeldung.Post;

@Path("/posts")
@RegisterRestClient(configKey = "post-api")
public interface PostRestClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Post> getAllPosts();
}
