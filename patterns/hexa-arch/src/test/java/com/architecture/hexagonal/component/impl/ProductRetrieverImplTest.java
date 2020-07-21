package com.architecture.hexagonal.component.impl;

import com.architecture.hexagonal.component.ProductPathConfiguration;
import com.architecture.hexagonal.component.ProductRetriever;
import com.architecture.hexagonal.component.ResourceRetriever;
import com.architecture.hexagonal.model.Product;
import com.architecture.hexagonal.properties.ProductPathProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductRetrieverImplTest {

    @Mock
    ResourceRetriever resourceRetriever;

    @Mock
    ProductPathConfiguration productPathConfiguration;

    @Mock
    ObjectMapper objectMapper;

    @InjectMocks
    ProductRetrieverImpl tested;

    @Test
    public void retrieveProductByIdProvidesExpectedJson() throws IOException {
        final ProductRetrieverImpl spy = spy(tested);

        int productId = 1;
        String productPath = "/products/1-product.json";
        String productJson = "{\"id\":1,\"name\":\"Product 1\",\"description\":\"This is a product description for product 1\",\"price\":12.99}";
        Product expected = mock(Product.class);

        when(productPathConfiguration.getProductPath(productId)).thenReturn(productPath);
        when(resourceRetriever.retrieveResource(productPath)).thenReturn(productJson);
        when(objectMapper.readValue(productJson, Product.class)).thenReturn(expected);

        Product actual = tested.retrieveById(productId);
        assertThat(actual).isSameAs(expected);

    }

}