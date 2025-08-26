package com.baeldung.streaming;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Controller
@RequestMapping("/reactive/files")
public class ReactiveStreamingController {

    private static final Path UPLOAD_DIR = Path.of("reactive-uploads");

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public Mono<String> uploadFileStreaming(@RequestPart("filePart") FilePart filePart) {
        return Mono.fromCallable(() -> {
            Path targetPath = UPLOAD_DIR.resolve(filePart.filename());
            Files.createDirectories(targetPath.getParent());
            return targetPath;
        }).flatMap(targetPath ->
          filePart.transferTo(targetPath)
            .thenReturn("Upload successful: " + filePart.filename())
        );
    }

    @GetMapping(value = "/download", produces = "multipart/mixed")
    public ResponseEntity<Flux<DataBuffer>> downloadFiles() {
        String boundary = "filesBoundary";

        List<Path> files = List.of(
          UPLOAD_DIR.resolve("file1.txt"),
          UPLOAD_DIR.resolve("file2.txt")
        );

        // Use concatMap to ensure files are streamed one after another, sequentially.
        Flux<DataBuffer> fileFlux = Flux.fromIterable(files)
          .concatMap(file -> {
              String partHeader = "--" + boundary + "\r\n" +
                "Content-Type: application/octet-stream\r\n" +
                "Content-Disposition: attachment; filename=\"" + file.getFileName() + "\"\r\n\r\n";

              Flux<DataBuffer> fileContentFlux = DataBufferUtils.read(file, new DefaultDataBufferFactory(), 4096);
              DataBuffer footerBuffer = new DefaultDataBufferFactory().wrap("\r\n".getBytes());

              // Build the flux for this specific part: header + content + footer
              return Flux.concat(
                Flux.just(new DefaultDataBufferFactory().wrap(partHeader.getBytes())),
                fileContentFlux,
                Flux.just(footerBuffer)
              );
          })
          // After all parts, concat the final boundary
          .concatWith(Flux.just(
            new DefaultDataBufferFactory().wrap(("--" + boundary + "--\r\n").getBytes())
          ));

        return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_TYPE, "multipart/mixed; boundary=" + boundary)
          .body(fileFlux);
    }
}