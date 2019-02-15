package com.baeldung.jooby.mvc;

import org.jooby.Result;
import org.jooby.Results;
import org.jooby.mvc.GET;
import org.jooby.mvc.Path;

@Path("/hello")
public class GetController {

	@GET
	public String hello() {
		return "Hello Baeldung";
	}

	@GET
	@Path("/home")
	public Result home() {
		return Results.html("welcome").put("model", new Object());
	}

}
