package com.baeldung.awss3updateobject.controller;

import com.baeldung.awss3updateobject.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class FileControllerUnitTest {

    private MockMvc mockMvc;

    @Mock
    private FileService fileService;

    @InjectMocks
    private FileController fileController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(fileController).build();
    }

    @Test
    public void givenValidMultipartFile_whenUploadedViaEndpoint_thenCorrectPathIsReturned() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt", "text/plain", "sample file content".getBytes());
        String expectedResult = "File Uploaded Successfully";

        when(fileService.uploadFile(multipartFile)).thenReturn(expectedResult);

        mockMvc.perform(multipart("/api/v1/file/upload").file(multipartFile))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    @Test
    public void givenValidMultipartFileAndExistingPath_whenUpdatedViaEndpoint_thenSamePathIsReturned() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt", "text/plain", "updated file content".getBytes());
        String filePath = "some/path/to/file";
        String expectedResult = "File Updated Successfully";

        when(fileService.updateFile(multipartFile, filePath)).thenReturn(expectedResult);

        mockMvc.perform(multipart("/api/v1/file/update")
                        .file(multipartFile)
                        .param("filePath", filePath))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }
}