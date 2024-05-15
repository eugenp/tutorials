package com.baeldung.longpolling;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockAsyncContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BakeryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenDeferredResultTimesOut_ThenErrorResponseIsRecieved() throws Exception {
        MvcResult asyncListener = mockMvc
          .perform(MockMvcRequestBuilders.get("/api/bake/cookie?bakeTime=6000"))
          .andExpect(request().asyncStarted())
          .andReturn();

        enableTimeout(asyncListener);

        String response = mockMvc
          .perform(asyncDispatch(asyncListener))
          .andReturn()
          .getResponse()
          .getContentAsString();

        assertThat(response)
          .isEqualTo("the bakery is not responding in allowed time");
    }

    @Test
    public void givenDeferredResultSuccessful_ThenSuccessResponseIsRecieved() throws Exception {
        MvcResult asyncListener = mockMvc
          .perform(MockMvcRequestBuilders.get("/api/bake/cookie?bakeTime=1000"))
          .andExpect(request().asyncStarted())
          .andReturn();

        String response = mockMvc
          .perform(asyncDispatch(asyncListener))
          .andReturn()
          .getResponse()
          .getContentAsString();

        assertThat(response)
          .isEqualTo("Bake for cookie complete and order dispatched. Enjoy!");
    }

    private static void enableTimeout(MvcResult asyncListener) throws IOException {
        ((MockAsyncContext) asyncListener
          .getRequest()
          .getAsyncContext())
          .getListeners()
          .get(0)
          .onTimeout(null);
    }
}
