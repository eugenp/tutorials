package com.baeldung.springsecurity.controller;

import javax.mvc.annotation.Controller;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/home")
@Controller
public class HomeController {

    @GET
    public String home() {
        return "home.jsp";
    }

    @GET
    @Path("/user")
    public String admin() {
        return "user.jsp";
    }

    @GET
    @Path("/admin")
    public String user() {
        return "admin.jsp";
    }

}
