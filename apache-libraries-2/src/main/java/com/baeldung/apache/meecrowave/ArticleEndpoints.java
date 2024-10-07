package com.baeldung.apache.meecrowave;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@RequestScoped
@Path("article")
public class ArticleEndpoints {

    @Inject
    ArticleService articleService;

    @GET
    public Response getArticle() {
        return Response.ok()
            .entity(new Article("name", "author"))
            .build();

    }

    @POST
    public Response createArticle(Article article) {
        return Response.status(Status.CREATED)
            .entity(articleService.createArticle(article))
            .build();
    }
}