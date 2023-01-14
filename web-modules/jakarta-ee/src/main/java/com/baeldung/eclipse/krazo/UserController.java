package com.baeldung.eclipse.krazo;

import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.binding.BindingResult;
import jakarta.mvc.security.CsrfProtected;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The class contains two controllers and a REST API
 */
@Path("users")
public class UserController {
    @Inject
    private BindingResult bindingResult;

    private static final List<User> users = new ArrayList<>();

    @Inject
    private Models models;

    /**
     * This is a controller. It displays a initial form to the user.
     * @return The view name
     */
    @GET
    @Controller
    public String showForm() {
        return "user.jsp";
    }

    /**
     * The  method handles the form submits
     * Handles HTTP POST and is CSRF protected. The client invoking this controller should provide a CSRF token.
     * @param user The user details that has to be stored
     * @return Returns a view name
     */
    @POST
    @Controller
    @CsrfProtected
    public String saveUser(@Valid @BeanParam User user) {
        if (bindingResult.isFailed()) {
            models.put("errors", bindingResult.getAllErrors());
            return "user.jsp";
        }
        String id = UUID.randomUUID().toString();
        user.setId(id);
        users.add(user);
        return "redirect:users/success";
    }

    /**
     * Handles a redirect view
     * @return The view name
     */
    @GET
    @Controller
    @Path("success")
    public String saveUserSuccess() {
        return "success.jsp";
    }

    /**
     * The REST API that returns all the user details in the JSON format
     * @return The list of users that are saved. The List<User>  is converted into Json Array.
     * If no user is present a empty array is returned
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers() {
        return users;
    }

}
