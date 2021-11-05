package com.baeldung.unsupportedmediatype;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class ApplicationUnitTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void JsonTestCase() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(  "{\n"
                                 + "    \"name\": \"Andy\",\n"
                                 + "    \"age\": 1,\n"
                                 + "    \"address\": \"Hello world\"\n"
                                 + "}"))
                .andExpect(status().isOk());
    }

    @Test
    public void JsonFailTestCase() throws Exception {// Because no content-type added
        mockMvc.perform(MockMvcRequestBuilders.post("/user/")
                        .content(  "{\n"
                                 + "    \"name\": \"Andy\",\n"
                                 + "    \"age\": 1,\n"
                                 + "    \"address\": \"Hello world\"\n"
                                 + "}"))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void XmlTestCase() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/")
                            .contentType(MediaType.APPLICATION_XML_VALUE)
                            .content("<user><name>Andy</name><age>1</age><address>Hello world</address></user>"))
                .andExpect(status().isOk());
    }

    @Test
    public void StringFailTestCase() throws Exception { // Because content-type is not supported
        mockMvc.perform(MockMvcRequestBuilders.post("/user/")
                            .contentType(MediaType.TEXT_PLAIN_VALUE)
                            .content("<user><name>Andy</name><age>1</age><address>Hello world</address></user>"))
                .andExpect(status().isUnsupportedMediaType());
    }
}
