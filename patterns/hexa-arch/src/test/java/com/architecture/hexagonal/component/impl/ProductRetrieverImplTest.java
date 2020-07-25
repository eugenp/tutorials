package com.architecture.hexagonal.component.impl;

import com.architecture.hexagonal.model.Product;
import com.architecture.hexagonal.model.ProductStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductRetrieverImplTest {

    @Mock
    ProductStore productStore;

    @InjectMocks
    ProductRetrieverImpl tested;

    @Test
    void retrieveByIdReturnsCorrectProduct() {

        //given
        Product expected = new Product(1, "Product 1", "This is a product description for product 1", 12.99);

        //when
        when(productStore.findProductById(1)).thenReturn(expected);

        Product actual = tested.retrieveById(1);

        //then
        assert (actual.equals(expected));
    }
}