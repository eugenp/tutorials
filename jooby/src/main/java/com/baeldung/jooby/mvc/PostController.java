package com.baeldung.jooby.mvc;

import org.jooby.mvc.POST;
import org.jooby.mvc.Path;

@Path("/submit")
public class PostController {

	@POST
	public String hello() {
		return "Submit Baeldung";
	}

}
