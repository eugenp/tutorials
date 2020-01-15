package org.nganga.bank.account.port;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.nganga.bank.account.domain.Account;
import org.nganga.bank.account.domain.Customer;

public class CustomerAccountDriverPort extends AbstractVerticle {

	public void start() {
		HttpServer httpServer = vertx.createHttpServer();
		Router router = Router.router(vertx);
		CustomerAccountService customerAccountService = new CustomerAccountService();
		ObjectMapper mapper = new ObjectMapper();
		Route customerEndpoint = router.post("/createCustomer");

		customerEndpoint.handler(BodyHandler.create()).handler(routingContext -> {

			JsonObject jo = routingContext.getBodyAsJson();

			String firstName = jo.getString("firstname");
			String surname = jo.getString("surname");
			String identity = jo.getString("identity");
			String email = jo.getString("email");

			Account customerAccount = new Account();
			customerAccount.setAccountNumber(identity + "1234567F");
			customerAccount.setCustomerId(identity);
			customerAccount.setDateOpened(LocalDate.now());
			Customer customer = Customer.builder().email(email).firstName(firstName).surnameName(surname)
					.account(customerAccount).build();
			customerAccountService.createAccount(customer);
			routingContext.response().end("Account Created");

		});

		Route getAccount = router.get("/customerAccount/:identity");
		getAccount.handler(BodyHandler.create()).handler(routingContext -> {
			String identity = routingContext.request().getParam("identity");
			Promise promise = Promise.promise();
			vertx.executeBlocking(req -> {
				List<Account> accounts = customerAccountService.getCustomerAccounts(identity);
				req.complete(accounts);

			}, res -> {
				if (res.succeeded()) {
					try {
						routingContext.response().end(mapper.writeValueAsString((List<Account>) res.result()));
					} catch (JsonProcessingException ex) {
						Logger.getLogger(CustomerAccountDriverPort.class.getName()).log(Level.SEVERE, null, ex);
					}
				} else {
					routingContext.response().setStatusCode(500);
				}
			});

		});

		httpServer.requestHandler(router).listen(9999);

	}
}
