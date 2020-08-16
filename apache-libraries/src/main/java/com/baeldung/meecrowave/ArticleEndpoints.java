package com.baeldung.meecrowave;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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