package com.baeldung.restclient;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.baeldung.Post;

@Path("/rest-client/consume-posts")
public class PostClientResource {

    @Inject
    @RestClient
    PostRestClient postRestClient;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Post> getPosts() {
        return postRestClient.getAllPosts();
    }
}
