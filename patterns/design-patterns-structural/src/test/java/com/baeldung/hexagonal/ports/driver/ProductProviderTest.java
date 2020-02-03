package com.baeldung.hexagonal.ports.driver;

import com.baeldung.hexagonal.adapter.driver.ProductProvider;
import com.baeldung.hexagonal.bridge.ProductResponse;
import com.baeldung.hexagonal.bridge.ProductService;
import com.baeldung.hexagonal.domain.ProductVO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductProviderTest {

    @Test
    void whenAskedForProducts_thenShouldProvideAvailableProducts() {
        //given
        ProductResponse response = new ProductResponse(new ProductVO("PS"));
        ProductService service = Mockito.mock(ProductService.class);
        Mockito.when(service.list()).thenReturn(Collections.singletonList(response));

        //when
        ProductProvider provider = new ProductProvider(service);

        //then
        assertEquals(1, provider.list().size());
        assertEquals("PS", provider.list().get(0).getProduct().getName());
    }
}