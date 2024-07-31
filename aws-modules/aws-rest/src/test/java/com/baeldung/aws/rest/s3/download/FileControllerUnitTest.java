package com.baeldung.aws.rest.s3.download;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.baeldung.aws.rest.s3.download.dto.FileDownloadResponse;
import com.baeldung.aws.rest.s3.download.service.S3FileServiceImpl;
import com.baeldung.aws.rest.s3.download.web.FileController;

@SpringBootTest(classes = { FileController.class, S3FileServiceImpl.class })
@AutoConfigureMockMvc
public class FileControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private S3FileServiceImpl fileService;

    @Test
    public void givenAnEncodedS3URL_whenRequestSentToGet_thenReturnsFiletoTheClientAsAttachmentWithTheOriginalNameAndExtension() throws Exception {
        // Mock FileDownloadResponse
        byte[] fileContent = "Test content".getBytes();
        String originalFilename = "test.txt";
        String contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;

        FileDownloadResponse mockResponse = new FileDownloadResponse(fileContent, originalFilename, contentType);
        when(fileService.downloadFile("mocked-s3-url")).thenReturn(mockResponse);

        // Perform request
        MockHttpServletRequestBuilder requestBuilder = get("/api/files/download/{encodedUrl}", "mocked-s3-url");

        mockMvc.perform(requestBuilder)
          .andDo(MockMvcResultHandlers.print())
          .andExpect(status().isOk())
          .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=test.txt"))
          .andExpect(header().string(HttpHeaders.CONTENT_TYPE, "application/octet-stream"))
          .andExpect(content().string("Test content"));
    }
}
