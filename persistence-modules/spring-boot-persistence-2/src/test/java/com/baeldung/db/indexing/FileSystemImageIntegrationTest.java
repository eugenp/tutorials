package com.baeldung.db.indexing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(classes = ImageArchiveApplication.class)
@AutoConfigureMockMvc
class FileSystemImageIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    FileLocationService fileLocationService;

    @Test
    void givenJpegImage_whenUploadIt_thenReturnItsId() throws Exception {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream image = classLoader.getResourceAsStream("baeldung.jpeg");

        MockMultipartFile jpegImage = new MockMultipartFile("image", "baeldung", MediaType.TEXT_PLAIN_VALUE, image);
        MockMultipartHttpServletRequestBuilder multipartRequest = MockMvcRequestBuilders.multipart("/file-system/image")
            .file(jpegImage);

        given(fileLocationService.save(jpegImage.getBytes(), "baeldung"))
            .willReturn(1L);

        MvcResult result = mockMvc.perform(multipartRequest)
            .andExpect(status().isOk())
            .andReturn();

        assertThat(result.getResponse()
            .getContentAsString())
                .isEqualTo("1");
    }

    @Test
    void givenBaeldungImage_whenDownloadIt_thenReturnTheImage() throws Exception {
        given(fileLocationService.find(1L))
            .willReturn(baeldungJpegResource());

        mockMvc.perform(MockMvcRequestBuilders
            .get("/file-system/image/1")
            .contentType(MediaType.IMAGE_JPEG_VALUE))
            .andExpect(status().isOk());
    }

    private FileSystemResource baeldungJpegResource() {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try {
            final URL resource = classLoader.getResource("baeldung.jpeg");
            if (resource != null) {
                return new FileSystemResource(new File(resource.toURI()).getAbsolutePath());
            }
        } catch (URISyntaxException ignore) {}
        return null;
    }

}
