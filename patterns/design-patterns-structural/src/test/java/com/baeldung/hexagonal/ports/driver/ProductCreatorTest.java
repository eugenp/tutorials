package com.baeldung.hexagonal.ports.driver;

import com.baeldung.hexagonal.adapter.driven.EmailNotifyingAdapter;
import com.baeldung.hexagonal.adapter.driver.ProductCreator;
import com.baeldung.hexagonal.bridge.ProductRequest;
import com.baeldung.hexagonal.bridge.ProductResponse;
import com.baeldung.hexagonal.bridge.ProductService;
import com.baeldung.hexagonal.domain.ProductVO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductCreatorTest {

    @Test
    void whenAskedToCreateProduct_thenShouldNotifyAndReturnProduct() {
        //given
        ProductVO vo = new ProductVO("PS");
        ProductService service = mock(ProductService.class);
        EmailNotifyingAdapter notifier = mock(EmailNotifyingAdapter.class);

        when(service.create(any()))
                .thenReturn(new ProductResponse(vo));
        when(notifier.sendNotification()).thenCallRealMethod();

        ProductRequest request = new ProductRequest(vo);

        //when
        ProductCreator creator = new ProductCreator(service, notifier);
        ProductResponse response = creator.execute(request);

        //then
        assertEquals(vo, response.getProduct());
        assertEquals(1, notifier.getCount());
    }
}