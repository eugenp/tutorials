package com.baeldung.contentsecuritypolicy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.servlet.http.HttpServletResponse;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest
@AutoConfigureMockMvc
@DisplayName("Content Security Policy Unit Tests")
@Import(ContentSecurityPolicySecurityConfiguration.class)
class ContentSecurityPolicyUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test to Check Bad URL")
    void whenWrongUri_thenThrow404() throws Exception {
        MvcResult result = mockMvc.perform(post("/reports").content("").contentType(MediaType.APPLICATION_JSON)).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Test to Check Page rendering")
    void whenGet_thenRenderPage() throws Exception {
        MvcResult result = mockMvc.perform(get("/").content("")).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals("text/html", MediaType.TEXT_HTML_VALUE);
    }

    @Test
    @DisplayName("Test to Check CSP headers")
    void whenGet_thenCheckCspHeaders() throws Exception {
        MvcResult result = mockMvc.perform(get("/").content("")).andReturn();
        HttpServletResponse response = result.getResponse();
        Collection<String> headers = response.getHeaderNames();

        assertNotNull(result);
        assertNotNull(headers);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("text/html", MediaType.TEXT_HTML_VALUE);
        assertTrue(headers.contains("Report-To"));
        assertTrue(headers.contains("Content-Security-Policy"));
    }
}
