package com.baeldung.webflux.filerecord;

import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.file.Paths;

@RestController
public class FileRecordController {

    private final FileRecordService fileRecordService;

    public FileRecordController(FileRecordService fileRecordService) {
        this.fileRecordService = fileRecordService;
    }

    @PostMapping("/upload-files")
    public Mono<String> uploadFileWithoutEntity(@RequestPart("files") Flux<FilePart> filePartFlux) {
        return filePartFlux.flatMap(file -> file.transferTo(Paths.get(file.filename())))
            .then(Mono.just("OK"))
            .onErrorResume(error -> Mono.just("Error uploading files"));
    }

    @PostMapping("/upload-files-entity")
    public Mono<FileRecord> uploadFileWithEntity(@RequestPart("files") Flux<FilePart> filePartFlux) {
        FileRecord fileRecord = new FileRecord();

        return filePartFlux.flatMap(filePart -> filePart.transferTo(Paths.get(filePart.filename()))
            .then(Mono.just(filePart.filename())))
            .collectList()
            .flatMap(filenames -> {
                fileRecord.setFilenames(filenames);
                return fileRecordService.save(fileRecord);
            })
            .onErrorResume(error -> Mono.error(error));
    }

    @GetMapping("/files/{id}")
    public Mono<FileRecord> geFilesById(@PathVariable("id") int id) {
        return fileRecordService.findById(id)
            .onErrorResume(error -> Mono.error(error));
    }
}
