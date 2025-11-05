package com.baeldung.panache;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/v2/articles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArticleResource {

	@Inject
	ArticleRepository articleRepository;

	@POST
	public Response create(Article article) {
		articleRepository.persist(article);
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
