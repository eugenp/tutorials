package com.baeldung.webflux;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.baeldung.webflux.controller.FooWebfluxController;
import com.baeldung.webflux.model.Foo;
import com.baeldung.webflux.service.FooWebClientService;

@RunWith(SpringRunner.class)
@WebFluxTest(FooWebfluxController.class)
@ContextConfiguration(classes = {FooWebClientService.class, Spring5WebfluxApplication.class})
public class FooWebfluxControllerTest {
    
    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void getFooStream() {
        List<Foo> fooList = webTestClient.get().uri("/foos")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .returnResult(Foo.class)
                .getResponseBody()
                .take(3)
                .collectList()
                .block();

        assertThat(fooList).allSatisfy(foo -> assertThat(foo.getName()).isNotBlank());
    }

}
