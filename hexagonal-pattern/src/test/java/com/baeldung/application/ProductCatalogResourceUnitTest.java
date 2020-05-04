package com.baeldung.application;

import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.baeldung.catalog.application.GetProductResponse;
import com.baeldung.catalog.domain.DomainProductService;
import com.baeldung.catalog.domain.Product;
import com.baeldung.catalog.domain.ProductService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import java.util.Collections;

import javax.json.bind.JsonbBuilder;

@QuarkusTest
public class ProductCatalogResourceUnitTest {

    private static final Product TEST_PRODUCT = new Product("1", "desc");

    @BeforeAll
    public static void setUp() {
        ProductService mock = Mockito.mock(DomainProductService.class);
        Mockito.when(mock.getProductById(TEST_PRODUCT.getId()))
            .thenReturn(TEST_PRODUCT);
        QuarkusMock.installMockForType(mock, ProductService.class);
    }

    @Test
    public void testGetProduct() {
        var expected = new GetProductResponse(TEST_PRODUCT.getId(),
            TEST_PRODUCT.getShortName(), 
            Collections.emptySet());
        String expectedJson = JsonbBuilder.create().toJson(expected);
        given().when()
            .get("/products/{id}", TEST_PRODUCT.getId())
            .then()
            .statusCode(200)
            .body(is(expectedJson));
    }
}