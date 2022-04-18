package com.baeldung.jersey.exceptionhandling.rest;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.baeldung.jersey.exceptionhandling.ExceptionHandlingConfig;
import com.baeldung.jersey.exceptionhandling.data.Stock;
import com.baeldung.jersey.exceptionhandling.data.Wallet;
import com.baeldung.jersey.exceptionhandling.rest.exceptions.IllegalArgumentExceptionMapper;
import com.baeldung.jersey.exceptionhandling.rest.exceptions.RestErrorResponse;
import com.baeldung.jersey.exceptionhandling.rest.exceptions.ServerExceptionMapper;

public class StocksResourceIntegrationTest extends JerseyTest {
    private static final Entity<String> EMPTY_BODY = Entity.json("");
    private static final Stock STOCK = new Stock("BAEL", 51.57);
    private static final String MY_WALLET = "MY-WALLET";
    private static final Wallet WALLET = new Wallet(MY_WALLET);
    private static final int INSUFFICIENT_AMOUNT = (int) (Wallet.MIN_CHARGE - 1);

    @Override
    protected Application configure() {
        return new ExceptionHandlingConfig();
    }

    private Invocation.Builder stocks(String path) {
        return target("/stocks" + path).request();
    }

    private Invocation.Builder wallets(String path, Object... args) {
        return target("/wallets" + String.format(path, args)).request();
    }

    private Entity<?> entity(Object object) {
        return Entity.entity(object, MediaType.APPLICATION_JSON_TYPE);
    }

    @Test
    public void whenMethodNotAllowed_thenCustomMessage() {
        Response response = stocks("").get();

        assertEquals(Status.METHOD_NOT_ALLOWED.getStatusCode(), response.getStatus());

        String content = response.readEntity(String.class);
        assertThat(content, containsString(ServerExceptionMapper.HTTP_405_MESSAGE));
    }

    @Test
    public void whenTickerNotExists_thenRestErrorResponse() {
        Response response = stocks("/UNDEFINED").get();

        assertEquals(Status.EXPECTATION_FAILED.getStatusCode(), response.getStatus());

        RestErrorResponse content = response.readEntity(RestErrorResponse.class);
        assertThat(content.getMessage(), startsWith(IllegalArgumentExceptionMapper.DEFAULT_MESSAGE));
    }

    @Test
    public void givenAmountLessThanMinimum_whenAddingToWallet_thenInvalidTradeException() {
        wallets("").post(entity(WALLET));
        Response response = wallets("/%s/%d", MY_WALLET, INSUFFICIENT_AMOUNT).put(EMPTY_BODY);

        assertEquals(Status.NOT_ACCEPTABLE.getStatusCode(), response.getStatus());

        String content = response.readEntity(String.class);
        assertThat(content, containsString(Wallet.MIN_CHARGE_MSG));
    }

    @Test
    public void givenInsifficientFunds_whenBuyingStock_thenWebApplicationException() {
        stocks("").post(entity(STOCK));
        wallets("").post(entity(WALLET));
        
        Response response = wallets("/%s/buy/%s", MY_WALLET, STOCK.getId()).post(EMPTY_BODY);
        assertEquals(Status.NOT_ACCEPTABLE.getStatusCode(), response.getStatus());

        RestErrorResponse content = response.readEntity(RestErrorResponse.class);
        assertNotNull(content.getSubject());

        HashMap<?, ?> subject = (HashMap<?, ?>) content.getSubject();
        assertEquals(subject.get("id"), WALLET.getId());
        assertTrue(WALLET.getBalance() < Wallet.MIN_CHARGE);
    }
}
