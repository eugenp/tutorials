package com.baeldung.boot.mvc.controllers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Import(HelloController.class)
@ExtendWith(SpringExtension.class)
public class HelloControllerUnitTest {

    @Autowired
    private HelloController helloController;

    @Test
    public void whenHelloIsInvokedWithCaio_thenReturn200AsStatusAndHelloCaioAsBody() {
        ResponseEntity response = this.helloController.hello("Caio");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Hello, Caio");
    }

}
