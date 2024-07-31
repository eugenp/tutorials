package com.baeldung.webservice;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.baeldung.webservice.generated.GetProductRequest;
import com.baeldung.webservice.generated.GetProductResponse;

@Endpoint
public class ProductEndpoint {

    private static final String NAMESPACE_URI = "http://baeldung.com/spring-boot-web-service";

    private final ProductRepository productRepository;

    public ProductEndpoint(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductRequest")
    @ResponsePayload
    public GetProductResponse getProduct(@RequestPayload GetProductRequest request) {
        GetProductResponse response = new GetProductResponse();
        response.setProduct(productRepository.findProduct(request.getId()));
        return response;
    }
}
