package com.baeldung.hexagonal.web;

import com.baeldung.hexagonal.domain.CreatePersonRequest;
import com.baeldung.hexagonal.domain.Person;
import com.baeldung.hexagonal.domain.PersonService;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * JAX-RS adapter for the {@link PersonService}.
 */
@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {

    private final PersonService service;

    @Inject
    PersonResource(PersonService service) {
        this.service = service;
    }

    @GET
    @Path("/{id}")
    public Person getById(@PathParam("id") int id) {
        return service.get(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Person create(CreatePersonRequest request) {
        return service.create(request);
    }
}
