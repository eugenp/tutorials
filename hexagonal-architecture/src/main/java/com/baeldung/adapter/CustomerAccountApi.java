package com.baeldung.adapter;

import com.baeldung.domain.CustomerAccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.baeldung.adapter.CommandWrapper;
import com.baeldung.domain.Account;

public class CustomerAccountApi extends AbstractVerticle {

    public void start() {
        HttpServer httpServer = vertx.createHttpServer();
        Router router = Router.router(vertx);
        CustomerAccountService customerAccountService = new CustomerAccountService();
        ObjectMapper mapper = new ObjectMapper();
        Route customerEndpoint = router.post("/createCustomer");

        customerEndpoint.handler(BodyHandler.create()).handler(routingContext -> {

            JsonObject jo = routingContext.getBodyAsJson();

            String response = CommandWrapper.createCustomerAccount(jo).execute();

            routingContext.response().end(response);

        });

        Route getAccount = router.get("/customerAccount/:identity");
        getAccount.handler(BodyHandler.create()).handler(routingContext -> {
            String identity = routingContext.request().getParam("identity");
            vertx.executeBlocking(req -> {
                List<Account> accounts = customerAccountService.getCustomerAccounts(identity);
                req.complete(accounts);

            }, res -> {
                if (res.succeeded()) {
                    try {
                        routingContext.response().end(mapper.writeValueAsString((List<Account>) res.result()));
                    } catch (JsonProcessingException ex) {
                        Logger.getLogger(CustomerAccountApi.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    routingContext.response().setStatusCode(500);
                }
            });

        });

        httpServer.requestHandler(router).listen(9999);

    }
}
