package com.baeldung.multiparttesting;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.baeldung.matrix.config.MatrixWebConfig;

@WebAppConfiguration
@ContextConfiguration(classes = { MatrixWebConfig.class, MultipartPostRequestController.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class MultipartPostRequestControllerUnitTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void whenFileUploaded_thenVerifyStatus() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "hello.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(multipart("/upload").file(file)).andExpect(status().isOk());
    }
}