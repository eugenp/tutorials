package com.baeldung.jooby.mvc;

import io.jooby.annotations.POST;
import io.jooby.annotations.Path;

@Path("/submit")
public class PostController {

    @POST
    public String hello() {
        return "Submit Baeldung";
    }
}
