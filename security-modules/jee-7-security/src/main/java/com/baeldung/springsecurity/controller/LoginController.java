package com.baeldung.springsecurity.controller;

import javax.mvc.annotation.Controller;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/auth/login")
@Controller
public class LoginController {

    @GET
    public String login() {
        return "login.jsp";
    }
}
