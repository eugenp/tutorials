package com.baeldung.db.indexing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(classes = ImageArchiveApplication.class)
@AutoConfigureMockMvc
class ImageIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ImageDbRepository imageRepository;

    @Test
    void givenBaeldungJpegImage_whenUploadIt_thenReturnItsId() throws Exception {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream image = classLoader.getResourceAsStream("baeldung.jpeg");

        MockMultipartHttpServletRequestBuilder multipartRequest = MockMvcRequestBuilders.multipart("/image")
            .file(new MockMultipartFile("image", "baeldung", MediaType.TEXT_PLAIN_VALUE, image));

        MvcResult result = mockMvc.perform(multipartRequest)
            .andExpect(status().isOk())
            .andReturn();

        assertThat(result.getResponse()
            .getContentAsString())
                .isEqualTo("1");
    }

    @Test
    void givenExistingImage_whenDownloadIt_thenReturnHttpStatusOk() throws Exception {
        given(imageRepository.findById(1L))
            .willReturn(Optional.of(baeldungImage()));

        mockMvc.perform(MockMvcRequestBuilders
            .get("/image/1")
            .contentType(MediaType.IMAGE_JPEG_VALUE))
            .andExpect(status().isOk());
    }

    private Image baeldungImage() throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        Image mockImage = new Image();
        mockImage.setContent(Files.readAllBytes(Paths.get(classLoader.getResource("baeldung.jpeg")
            .getFile())));
        return mockImage;
    }

}
