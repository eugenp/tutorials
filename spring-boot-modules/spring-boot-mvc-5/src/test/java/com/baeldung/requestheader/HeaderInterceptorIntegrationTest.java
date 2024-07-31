package com.baeldung.requestheader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { HeaderInterceptorApplication.class })
@WebAppConfiguration
public class HeaderInterceptorIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
          .build();
    }

    @Test
    public void givenARequestWithOperatorHeader_whenWeCallFooEndpoint_thenOperatorIsExtracted() throws Exception {
        MockHttpServletResponse response = this.mockMvc.perform(get("/foo").header("operator", "John.Doe"))
          .andDo(print())
          .andReturn()
          .getResponse();

        assertThat(response.getContentAsString()).isEqualTo("hello, John.Doe");
    }

    @Test
    public void givenARequestWithOperatorHeader_whenWeCallBarEndpoint_thenOperatorIsExtracted() throws Exception {
        MockHttpServletResponse response = this.mockMvc.perform(get("/bar").header("operator", "John.Doe"))
          .andDo(print())
          .andReturn()
          .getResponse();

        assertThat(response.getContentAsString()).isEqualTo("hello, John.Doe");
    }

    @Test
    public void givenARequestWithOperatorHeader_whenWeCallBuzzEndpoint_thenOperatorIsIntercepted() throws Exception {
        MockHttpServletResponse response = this.mockMvc.perform(get("/buzz").header("operator", "John.Doe"))
          .andDo(print())
          .andReturn()
          .getResponse();

        assertThat(response.getContentAsString()).isEqualTo("hello, John.Doe");
    }

}
