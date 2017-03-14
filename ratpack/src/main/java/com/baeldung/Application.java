package com.baeldung;

import ratpack.http.MutableHeaders;
import ratpack.server.RatpackServer;

public class Application {

	public static void main(String... args) throws Exception {
		RatpackServer.start(server -> server.handlers(chain -> chain.all(ctx -> {
			MutableHeaders headers = ctx.getResponse().getHeaders();
			headers.set("Access-Control-Allow-Origin", "*");
			headers.set("Accept-Language", "en-us");
			headers.set("Accept-Charset", "UTF-8");
			ctx.next();
		})
		.get(ctx -> ctx.render("Welcome to baeldung ratpack!!!"))
		.get(":name", ctx -> ctx.render("Hello " + ctx.getPathTokens().get("name") + "!!!"))
		.post(":amount", ctx -> ctx.render(" Amount $" + ctx.getPathTokens().get("amount") + " added successfully !!!"))
		));
	}

}
