package com.baeldung.webclient;

import java.net.URI;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "productsBlocking", url = "http://localhost:8080")
public interface ProductsFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/slow-service-products", produces = "application/json")
    List<Product> getProductsBlocking(URI baseUrl);
}
