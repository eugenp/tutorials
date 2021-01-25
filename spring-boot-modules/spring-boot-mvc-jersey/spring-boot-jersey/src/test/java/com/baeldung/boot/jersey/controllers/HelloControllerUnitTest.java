package com.baeldung.boot.jersey.controllers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import javax.xml.ws.Response;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Import(HelloController.class)
@ExtendWith(SpringExtension.class)
public class HelloControllerUnitTest {

    @Autowired
    private HelloController helloController;

    @Test
    public void helloTest() {
        Response response = this.helloController.hello("Caio");
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getEntity()).isEqualTo("Hello, Caio");
    }

}
