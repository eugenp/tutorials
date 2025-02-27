package com.baeldung.webflux.filerecord;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = FileRecordController.class)
@ContextConfiguration(classes = { FileRecordController.class })
@ComponentScan("com.baeldung.webflux.filerecord")
@AutoConfigureWebTestClient
class FileRecordControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private FileRecordService fileRecordService;

    @Test
    public void givenUploadFilesWithEntity_whenRequestIsValid_thenReturnCreated() throws Exception {

        FileRecord fileRecord = new FileRecord();
        MockMultipartFile firstFile = new MockMultipartFile("file", "baeldungdata.txt", MediaType.TEXT_PLAIN_VALUE, "Test file content".getBytes());
        MockMultipartFile secondFile = new MockMultipartFile("file", "baeldungdata.pdf", MediaType.TEXT_PLAIN_VALUE, "Test file content".getBytes());
        List<MockMultipartFile> files = List.of(firstFile, secondFile);
        fileRecord.setFilenames(files.stream()
            .map(MockMultipartFile::getOriginalFilename)
            .toList());

        Mono<FileRecord> fileRecordMono = Mono.just(fileRecord);
        when(fileRecordService.save(any(FileRecord.class))).thenReturn(fileRecordMono);

        webTestClient.post()
            .uri("/upload-files-entity")
            .body(Mono.just(fileRecord), FileRecord.class)
            .exchange()
            .expectBody(FileRecord.class);

    }

    @Test
    public void givenUploadFiles_whenRequestIsValid_thenReturnOk() throws Exception {

        MockMultipartFile firstFile = new MockMultipartFile("file", "baeldungdata.txt", MediaType.TEXT_PLAIN_VALUE, "Test file content".getBytes());
        MockMultipartFile secondFile = new MockMultipartFile("file", "baeldungdata.pdf", MediaType.TEXT_PLAIN_VALUE, "Test file content".getBytes());
        LinkedMultiValueMap<String, MockMultipartFile> multipartData = new LinkedMultiValueMap<>();
        multipartData.add("file", firstFile);
        multipartData.add("file", secondFile);

        webTestClient.post()
            .uri("/upload-files")
            .bodyValue(multipartData)
            .exchange()
            .expectStatus()
            .isOk();
    }

    @Test
    public void givenUploadFilesWithEntity_whenRequestFileIsFindById_thenReturnOk() throws Exception {

        FileRecord fileRecord = new FileRecord();
        MockMultipartFile firstFile = new MockMultipartFile("file", "baeldungdata.txt", MediaType.TEXT_PLAIN_VALUE, "Test file content".getBytes());
        MockMultipartFile secondFile = new MockMultipartFile("file", "baeldungdata.pdf", MediaType.TEXT_PLAIN_VALUE, "Test file content".getBytes());
        List<MockMultipartFile> files = List.of(firstFile, secondFile);
        fileRecord.setId(1);
        fileRecord.setFilenames(files.stream()
            .map(MockMultipartFile::getOriginalFilename)
            .toList());

        Mono<FileRecord> fileRecordMono = Mono.just(fileRecord);

        when(fileRecordService.findById(1)).thenReturn(fileRecordMono);

        webTestClient.get()
            .uri("/files/{id}", 1)
            .exchange()
            .expectStatus()
            .isOk();
    }

}