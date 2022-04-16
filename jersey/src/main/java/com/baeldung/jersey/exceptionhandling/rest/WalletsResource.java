package com.baeldung.jersey.exceptionhandling.rest;

import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.baeldung.jersey.exceptionhandling.data.Stock;
import com.baeldung.jersey.exceptionhandling.data.Wallet;
import com.baeldung.jersey.exceptionhandling.repo.Db;
import com.baeldung.jersey.exceptionhandling.rest.exceptions.InvalidTradeException;
import com.baeldung.jersey.exceptionhandling.rest.exceptions.RestErrorResponse;
import com.baeldung.jersey.exceptionhandling.service.Repository;

@Path("/wallets")
public class WalletsResource {
    private static final Db<Stock> stocks = Repository.STOCKS_DB;
    private static final Db<Wallet> wallets = Repository.WALLETS_DB;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(Wallet wallet) {
        wallets.save(wallet);

        return Response.ok(wallet)
            .build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") String id) {
        Optional<Wallet> wallet = wallets.findById(id);
        wallet.orElseThrow(IllegalArgumentException::new);

        return Response.ok(wallet.get())
            .build();
    }

    @PUT
    @Path("/{id}/{amount}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response putAmount(@PathParam("id") String id, @PathParam("amount") Double amount) {
        Optional<Wallet> wallet = wallets.findById(id);
        wallet.orElseThrow(IllegalArgumentException::new);

        if (amount < Wallet.MIN_CHARGE) {
            throw new InvalidTradeException(Wallet.MIN_CHARGE_MSG);
        }
        
        wallet.get()
            .addBalance(amount);
        wallets.save(wallet.get());

        return Response.ok(wallet)
            .build();
    }

    @POST
    @Path("/{wallet}/buy/{ticker}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response postBuyStock(@PathParam("wallet") String walletId, @PathParam("ticker") String id) {
        Optional<Stock> stock = stocks.findById(id);
        stock.orElseThrow(InvalidTradeException::new);

        Optional<Wallet> w = wallets.findById(walletId);
        w.orElseThrow(InvalidTradeException::new);

        Wallet wallet = w.get();
        Double price = stock.get()
            .getPrice();

        if (!wallet.hasFunds(price)) {
            RestErrorResponse response = new RestErrorResponse();
            response.setSubject(wallet);
            response.setMessage("insufficient balance");
            throw new WebApplicationException(Response.status(Status.NOT_ACCEPTABLE)
                .entity(response)
                .build());
        }

        wallet.addBalance(-price);
        wallets.save(wallet);

        return Response.ok(wallet)
            .build();
    }
}
