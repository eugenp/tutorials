package com.baeldung.jooby.mvc;

import java.util.HashMap;

import io.jooby.ModelAndView;
import io.jooby.annotations.GET;
import io.jooby.annotations.Path;

@Path("/hello")
public class GetController {

    @GET
    public String hello() {
        return "Hello Baeldung";
    }

    @GET
    @Path("/home")
    public ModelAndView home() {
        return new ModelAndView("welcome.html", new HashMap<>());
    }

}
