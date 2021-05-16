package com.baeldung.controller.rss;

import com.baeldung.spring.configuration.ApplicationConfiguration;
import com.baeldung.spring.configuration.EmailConfiguration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig(classes={ApplicationConfiguration.class, EmailConfiguration.class})
public class ArticleRssIntegrationTest {
    public static final String APPLICATION_RSS_XML = "application/rss+xml";
    public static final String APPLICATION_RSS_JSON = "application/rss+json";
    public static final String APPLICATION_RSS_XML_CHARSET_UTF_8 = "application/rss+xml;charset=UTF-8";

    @Autowired
    private WebApplicationContext webAppContext;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext)
                .build();
    }

    @Test
    public void whenRequestingXMLFeed_thenContentTypeIsOk() throws Exception {
        mockMvc.perform(get("/rss2").accept(APPLICATION_RSS_XML))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_RSS_XML_CHARSET_UTF_8));
    }

    @Test
    public void whenRequestingJSONFeed_thenContentTypeIsOk() throws Exception {
        mockMvc.perform(get("/rss2").accept(APPLICATION_RSS_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_RSS_JSON));
    }
}