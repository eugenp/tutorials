package com.baeldung.aws.rest.s3.download;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.aws.rest.s3.download.dto.FileData;
import com.baeldung.aws.rest.s3.download.dto.FileDownloadResponse;
import com.baeldung.aws.rest.s3.download.dto.S3ObjectRequest;
import com.baeldung.aws.rest.s3.download.dto.S3ResponseReader;
import com.baeldung.aws.rest.s3.download.service.S3FileServiceImpl;

import software.amazon.awssdk.services.s3.model.GetObjectRequest;

@ExtendWith(SpringExtension.class)
public class S3FileServiceImplUnitTest {

    @MockBean
    private S3ResponseReader s3ResponseReader;

    private S3FileServiceImpl fileService;

    @Test
    public void givenAStringURL_whenDownloadFileService_thenReturnsFileDataAndMetaData() throws IOException {
        fileService = new S3FileServiceImpl(s3ResponseReader);
        // Mock S3 URL
        String s3Url = "s3://my-bucket/test.txt";

        // Mock response from FileReader
        byte[] expectedFileContent = "Mock file content".getBytes();
        String expectedContentType = "application/octet-stream";
        String expectedContentDisposition = "attachment; filename=test.txt";

        FileData mockFileData = new FileData(expectedFileContent, expectedContentType, expectedContentDisposition,
          GetObjectRequest.builder()
            .bucket("my-bucket")
            .key("test.txt")
            .build());
        when(s3ResponseReader.readResponse(any(S3ObjectRequest.class))).thenReturn(mockFileData);

        // Perform the download
        FileDownloadResponse fileDownloadResponse = fileService.downloadFile(s3Url);

        // Assert the file content
        assertEquals("Mock file content", new String(fileDownloadResponse.getFileContent()));

        // Assert metadata
        assertEquals(expectedContentType, fileDownloadResponse.getContentType());
        assertEquals("test.txt", fileDownloadResponse.getOriginalFilename());
    }
}
