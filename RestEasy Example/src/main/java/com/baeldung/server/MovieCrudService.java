package com.baeldung.server;

import com.baeldung.model.Movie;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Path("/movies")
public class MovieCrudService {


    private Map<String,Movie> inventory = new HashMap<String, Movie>();


    @GET
    @Path("/getinfo")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Movie movieByImdbID(@QueryParam("imdbId") String imdbID){

        System.out.println("*** Calling  getinfo for a given ImdbID***");

        if(inventory.containsKey(imdbID)){
            return inventory.get(imdbID);
        }else return null;

    }


    @POST
    @Path("/addmovie")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response addMovie(Movie movie){

        System.out.println("*** Calling  addMovie ***");

        if (null!=inventory.get(movie.getImdbId())){
            return Response.status(Response.Status.NOT_MODIFIED)
                    .entity("Movie is Already in the database.").build();
        }

        inventory.put(movie.getImdbId(),movie);

        return Response.status(Response.Status.CREATED).build();
    }


    @PUT
    @Path("/updatemovie")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response updateMovie(Movie movie){

        System.out.println("*** Calling  updateMovie ***");

        if (null==inventory.get(movie.getImdbId())){
            return Response.status(Response.Status.NOT_MODIFIED)
                    .entity("Movie is not in the database.\nUnable to Update").build();
        }
        inventory.put(movie.getImdbId(),movie);
        return Response.status(Response.Status.OK).build();

    }


    @DELETE
    @Path("/deletemovie")
    public Response deleteMovie(@QueryParam("imdbId") String imdbID){

        System.out.println("*** Calling  deleteMovie ***");

        if (null==inventory.get(imdbID)){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Movie is not in the database.\nUnable to Delete").build();
        }

        inventory.remove(imdbID);
        return Response.status(Response.Status.OK).build();
    }


    @GET
    @Path("/listmovies")
    @Produces({"application/json"})
    public List<Movie>  listMovies(){

        return inventory.values().stream().collect(Collectors.toCollection(ArrayList::new));

    }



}
