package com.baeldung.mongoclient;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/articles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArticleResource {

    @Inject
    ArticleRepository articleRepository;

    @POST
    public Response create(Article article) {
        articleRepository.create(article);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public List<Article> listAll() {
        return articleRepository.listAll();
    }

    @PUT
    public Response update(Article updatedArticle) {
        articleRepository.update(updatedArticle);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        articleRepository.delete(id);
        return Response.noContent().build();
    }
}