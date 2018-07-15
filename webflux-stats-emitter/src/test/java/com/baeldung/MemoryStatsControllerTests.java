package com.baeldung;

import com.baeldung.controller.MemoryStatsController;
import com.baeldung.service.EventsEmitter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Controller for MemoryStatsController.
 */
@RunWith(SpringRunner.class)
@WebFluxTest(controllers = MemoryStatsController.class)
public class MemoryStatsControllerTests {

    @Autowired private WebTestClient webTestClient;

    @MockBean
    EventsEmitter emitterMock;

    @Test
    public void checkMemoryStatsAPI_Returns200Response() {
        webTestClient.get().uri("/api/memoryStats").exchange().expectStatus().isOk();
    }
}
