package com.baeldung.dropwizard.introduction.resource;

import com.baeldung.dropwizard.introduction.domain.Brand;
import com.baeldung.dropwizard.introduction.repository.BrandRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/brands")
@Produces(MediaType.APPLICATION_JSON)
public class BrandResource {
    private final int defaultSize;
    private final BrandRepository brandRepository;

    public BrandResource(final int defaultSize, final BrandRepository brandRepository) {
        this.defaultSize = defaultSize;
        this.brandRepository = brandRepository;

    }

    @GET
    public List<Brand> getBrands(@QueryParam("size") final Optional<Integer> size) {
        return brandRepository.findAll(size.orElse(defaultSize));
    }

    @GET
    @Path("/{id}")
    public Brand getById(@PathParam("id") final Long id) {
        return brandRepository
          .findById(id)
          .orElseThrow(RuntimeException::new);
    }
}
