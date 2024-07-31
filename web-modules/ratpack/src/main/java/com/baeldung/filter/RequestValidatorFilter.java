package com.baeldung.filter;

import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.http.MutableHeaders;

public class RequestValidatorFilter implements Handler {

	@Override
	public void handle(Context ctx) throws Exception {
		MutableHeaders headers = ctx.getResponse().getHeaders();
	    headers.set("Access-Control-Allow-Origin", "*");
		ctx.next();
	}

}
