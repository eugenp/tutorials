package com.baeldung.keycloaksoap;

import com.baeldung.DeleteProductRequest;
import com.baeldung.DeleteProductResponse;
import com.baeldung.GetProductDetailsRequest;
import com.baeldung.GetProductDetailsResponse;
import com.baeldung.Product;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.annotation.security.RolesAllowed;
import java.util.Map;

@Endpoint
public class ProductsEndpoint {

    private final Map<String, Product> productMap;

    public ProductsEndpoint(Map<String, Product> productMap) {
        this.productMap = productMap;
    }

    @RolesAllowed("user")
    @PayloadRoot(namespace = "http://www.baeldung.com/springbootsoap/keycloak", localPart = "getProductDetailsRequest")
    @ResponsePayload
    public GetProductDetailsResponse getProductDetails(@RequestPayload GetProductDetailsRequest request) {
        GetProductDetailsResponse response = new GetProductDetailsResponse();
        response.setProduct(productMap.get(request.getId()));
        return response;
    }

    @RolesAllowed("admin")
    @PayloadRoot(namespace = "http://www.baeldung.com/springbootsoap/keycloak", localPart = "deleteProductRequest")
    @ResponsePayload
    public DeleteProductResponse deleteProduct(@RequestPayload DeleteProductRequest request) {
        DeleteProductResponse response = new DeleteProductResponse();
        response.setMessage("Success! Deleted the product with the id - "+request.getId());
        return response;
    }
}
