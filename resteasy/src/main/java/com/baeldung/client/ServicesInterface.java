package com.baeldung.client;

import com.baeldung.model.Movie;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/movies")
public interface ServicesInterface {

    @GET
    @Path("/getinfo")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    Movie movieByImdbId(@QueryParam("imdbId") String imdbId);

    @GET
    @Path("/listmovies")
    @Produces({ "application/json" })
    List<Movie> listMovies();

    @POST
    @Path("/addmovie")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    Response addMovie(Movie movie);

    @PUT
    @Path("/updatemovie")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    Response updateMovie(Movie movie);

    @DELETE
    @Path("/deletemovie")
    Response deleteMovie(@QueryParam("imdbId") String imdbId);

}
