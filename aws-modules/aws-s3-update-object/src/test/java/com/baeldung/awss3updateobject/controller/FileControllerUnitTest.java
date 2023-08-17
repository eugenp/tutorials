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
        MockMultipartFile multipartFile = new MockMultipartFile("multipartFile", "test.txt",
                "text/plain", "test data".getBytes());

        when(fileService.uploadFile(any(MultipartFile.class))).thenReturn("/documents/test.txt");

        mockMvc.perform(multipart("/file/upload").file(multipartFile))
                .andExpect(status().isOk())
                .andExpect(content().string("/documents/test.txt"));
    }

    @Test
    public void givenValidMultipartFileAndExistingPath_whenUpdatedViaEndpoint_thenSamePathIsReturned() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("multipartFile", "test.txt",
                "text/plain", "test update data".getBytes());
        String existingFilePath = "/documents/existingFile.txt";

        when(fileService.updateFile(any(MultipartFile.class), eq(existingFilePath))).thenReturn(existingFilePath);

        mockMvc.perform(multipart("/file/update")
                        .file(multipartFile)
                        .param("exitingFilePath", existingFilePath))
                .andExpect(status().isOk())
                .andExpect(content().string(existingFilePath));
    }
}