package com.baeldung.quarkus.rbac.api;

import com.baeldung.quarkus.rbac.users.Role;
import com.baeldung.quarkus.rbac.users.UserService;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.stream.Collectors;

@Path("/secured")
public class SecureResourceController {

    private final UserService userService;
    private final SecurityIdentity securityIdentity;

    public SecureResourceController(UserService userService, SecurityIdentity securityIdentity) {
        this.userService = userService;
        this.securityIdentity = securityIdentity;
    }

    @GET
    @Path("/resource")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"VIEW_ADMIN_DETAILS"})
    public String get() {
        return "Hello world, here are some details about the admin!";
    }

    @GET
    @Path("/resource/user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"VIEW_USER_DETAILS"})
    public Message getUser() {
        return new Message("Hello "+securityIdentity.getPrincipal().getName()+"!");
    }

    @POST
    @Path("/resource")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("${customer.send.message.permission:SEND_MESSAGE}")
    public Response getUser(Message message) {
        return Response.ok(message).build();
    }

    @POST
    @Path("/user")
    @RolesAllowed({"CREATE_USER"})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postUser(@Valid final UserDto userDto) {

        final var user = userService.createUser(userDto);

        final var roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());

        return Response.ok(new UserResponse(user.getUsername(), roles)).build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response login(@Valid final LoginDto loginDto) {
        if (userService.checkUserCredentials(loginDto.username(), loginDto.password())) {
            final var user = userService.findByUsername(loginDto.username());
            final var token = userService.generateJwtToken(user);
            return Response.ok().entity(new TokenResponse("Bearer " + token,"3600")).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity(new Message("Invalid credentials")).build();
        }
    }
}
