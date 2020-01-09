package com.baeldung.openliberty.user;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.baeldung.openliberty.user.dao.UserDao;
import com.baeldung.openliberty.user.model.User;

@RequestScoped
@Path("user")
public class UserResource {

    @Inject
    UserService userService;

    @Inject
    private UserDao userDAO;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser() {
        return userService.getUser();
    }

    /**
     * This method creates a new user from the submitted data (firstName, lastName and
     * email)
     */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response addNewUser(@FormParam("firstName") String firstName,
        @FormParam("lastName") String lastName, @FormParam("email") String email) {
        System.out.println(firstName);
        System.out.println(lastName);
        System.out.println(email);
        User newUser = new User(firstName, lastName, email);
        userDAO.createUser(newUser);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
