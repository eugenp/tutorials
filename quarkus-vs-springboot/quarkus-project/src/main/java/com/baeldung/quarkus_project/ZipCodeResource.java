package com.baeldung.quarkus_project;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.jboss.logging.Logger;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/zipcode")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ZipCodeResource {

    private ZipCodeRepo zipRepo;

    public ZipCodeResource(ZipCodeRepo zipRepo) {
            this.zipRepo = zipRepo;
        }

    @GET
    @Path("/{zipcode}")
    public Uni<ZipCode> findById(@PathParam("zipcode") String zipcode) {
        return zipRepo.findById(zipcode);
    }

    @GET
    @Path("/by_city")
    public Multi<ZipCode> postZipCode(@QueryParam("city") String city) {
        return zipRepo.findByCity(city);
    }

    @POST
    @Transactional
    public Uni<ZipCode> create(ZipCode zipCode) {
        return zipRepo.findById(zipCode.getZip())
            .onItem()
            .ifNull()
            .switchTo(createZipCode(zipCode));
    }

    private Uni<ZipCode> createZipCode(ZipCode zipCode) {
        return Uni.createFrom().deferred(() -> zipRepo.save(zipCode));
    }
}