package com.baeldung.produceimage;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = ImageApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DataProducerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenJpgTrue_whenGetImageDynamicType_ThenContentTypeIsJpg() throws Exception {
        mockMvc.perform(get("/get-image-dynamic-type?jpg=true"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG))
                .andExpect(header().stringValues(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE));
    }

    @Test
    void givenJpgFalse_whenGetImageDynamicType_ThenContentTypeIsFalse() throws Exception {
        mockMvc.perform(get("/get-image-dynamic-type?jpg=false"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_PNG))
                .andExpect(header().stringValues(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE));
    }

}
