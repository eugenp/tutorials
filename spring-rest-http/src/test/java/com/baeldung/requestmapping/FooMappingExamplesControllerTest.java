package com.baeldung.requestmapping;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(FooMappingExamplesController.class)
public class FooMappingExamplesControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void givenAcceptsJson_whenGetDuplicate_thenJsonResponseReturned() throws Exception {
        mvc.perform(get("/ex/foos/duplicate")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("{\"message\":\"Duplicate\"}"));
    }

    @Test
    public void givenAcceptsXml_whenGetDuplicate_thenXmlResponseReturned() throws Exception {
        mvc.perform(get("/ex/foos/duplicate")
            .accept(MediaType.APPLICATION_XML))
            .andExpect(status().isOk())
            .andExpect(content().string("<message>Duplicate</message>"));
    }
}
