package com.baeldung.reactive.controller;

import com.baeldung.reactive.model.Image;
import com.baeldung.reactive.model.ImageEvent;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ImageControllerTest {

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private ImageController imageController;

    private WebTestClient client;

    @Before
    public void setup() {
        mongoOperations.dropCollection(Image.class);

        Arrays.asList(
                new Image(UUID.randomUUID().toString(), "im1"),
                new Image(UUID.randomUUID().toString(), "im2"),
                new Image(UUID.randomUUID().toString(), "im3"),
                new Image(UUID.randomUUID().toString(), "im4")
        ).forEach(mongoOperations::insert);

        client = WebTestClient.bindToController(imageController).build();
    }

    @Test
    public void whenGetExistingImage_thenOkResponse() {
        mongoOperations.insert(new Image("1", "im1"));

        client.get()
                .uri("/images/1")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void whenGetImageEvents_thenOkStatusResponse_andResponseBodyNotEmpty() {
        FluxExchangeResult<ImageEvent> result = client.get()
                .uri("/images/1/events")
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(ImageEvent.class);
        assertThat(result).isNotNull();
        assertThat(result.getResponseBody()).isNotNull();
    }
}
