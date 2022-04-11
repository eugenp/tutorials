package com.baeldung.jersey.exceptionhandling.rest;

import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.baeldung.jersey.exceptionhandling.data.Stock;
import com.baeldung.jersey.exceptionhandling.repo.Db;
import com.baeldung.jersey.exceptionhandling.service.Repository;

@Path("/stocks")
public class StocksResource {
    private static final Db<Stock> stocks = Repository.STOCKS_DB;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(Stock stock) {
        stocks.save(stock);

        return Response.ok(stock)
            .build();
    }

    @GET
    @Path("/{ticker}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("ticker") String id) {
        Optional<Stock> stock = stocks.findById(id);
        stock.orElseThrow(IllegalArgumentException::new);

        return Response.ok(stock.get())
            .build();
    }
}
