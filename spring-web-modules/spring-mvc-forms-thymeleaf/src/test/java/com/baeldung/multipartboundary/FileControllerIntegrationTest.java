package com.baeldung.multipartboundary;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.baeldung.multipartboundary.FileController.FILES;

@WebMvcTest(FileController.class)
class FileControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenFormData_whenPost_thenReturns200OK() throws Exception {
        String fileContent = "8cf95720000eda17,1234,true";

        MockMultipartFile file = new MockMultipartFile("file", "import.csv", MediaType.APPLICATION_OCTET_STREAM_VALUE, fileContent.getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart(FILES)
                        .file(file)
                        .param("fileDescription", "Records"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("files/success"));
    }

}