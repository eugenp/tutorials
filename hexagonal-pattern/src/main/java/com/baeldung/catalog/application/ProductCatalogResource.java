package com.baeldung.catalog.application;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.baeldung.catalog.domain.Product;
import com.baeldung.catalog.domain.ProductService;

@Path("/products")
public class ProductCatalogResource {
    private ProductService productService;
    
    @Inject
    public ProductCatalogResource(ProductService productService) {
        this.productService = productService;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public GetProductResponse getProduct(@PathParam("id") String id) {
        Product product = productService.getProductById(id);
        return new GetProductResponse(product.getId(), product.getShortName(), product.getProductPrices());
    }
}