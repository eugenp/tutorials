package com.baeldung.html;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HtmlController.class)
class HtmlControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    private final String expectedHtmlResponse =
            "<html>\n" + "<header><title>Welcome</title></header>\n" +
            "<body>\n" + "Hello world\n" + "</body>\n" + "</html>";

    @Test
    void whenGETRequestToCorrectURL_thenReturnCorrectWelcomeMessage() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/welcome"))
                .andExpect(status().isOk())
                .andReturn();

        String resultDOW = result.getResponse().getContentAsString();
        assertEquals(expectedHtmlResponse, resultDOW);
    }
}
