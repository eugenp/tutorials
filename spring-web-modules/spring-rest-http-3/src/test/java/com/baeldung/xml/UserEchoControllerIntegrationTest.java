package com.baeldung.xml;

import static org.assertj.core.api.Assertions.assertThat;

import com.baeldung.xml.controller.User;
import com.baeldung.xml.controller.UserEchoController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(UserEchoController.class)
class UserEchoControllerIntegrationTest {

    private static final String URI = "/users";
    private static final User USER = new User(1L, "John", "Doe");
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final XmlMapper XML_MAPPER = new XmlMapper();

    @Autowired
    private UserEchoController controller;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenContextStartBeansArePresent() {
        assertThat(controller).isNotNull();
        assertThat(mockMvc).isNotNull();
    }

    @Test
    void givenEndpointWhenPostJsonUserReceiveCorrectResponse() throws Exception {
        final String payload = JSON_MAPPER.writeValueAsString(USER);
        MockHttpServletRequestBuilder builder =
            MockMvcRequestBuilders.post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload);

        final MvcResult mvcResult = this.mockMvc.perform(builder)
            .andExpect(MockMvcResultMatchers.status()
                .isCreated()).andReturn();
        final User actual
            = JSON_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), User.class);
        assertThat(actual).isEqualTo(USER);
    }
    @Test
    void givenEndpointWhenPostXmlUserReceiveCorrectResponse() throws Exception {
        final String payload = XML_MAPPER.writeValueAsString(USER);
        MockHttpServletRequestBuilder builder =
            MockMvcRequestBuilders.post(URI)
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML)
                .content(payload);

        final MvcResult mvcResult = this.mockMvc.perform(builder)
            .andExpect(MockMvcResultMatchers.status()
                .isCreated()).andReturn();
        final User actual
            = XML_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), User.class);
        assertThat(actual).isEqualTo(USER);
    }

}
