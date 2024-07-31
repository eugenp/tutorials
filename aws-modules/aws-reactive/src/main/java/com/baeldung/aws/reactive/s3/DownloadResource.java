/**
 * 
 */
package com.baeldung.aws.reactive.s3;

import java.nio.ByteBuffer;
import java.util.Map.Entry;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.core.async.AsyncResponseTransformer;
import software.amazon.awssdk.http.SdkHttpResponse;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

/**
 * @author Philippe
 *
 */
@RestController
@RequestMapping("/inbox")
@Slf4j
public class DownloadResource {

    private final S3AsyncClient s3client;
    private final S3ClientConfigurarionProperties s3config;

    public DownloadResource(S3AsyncClient s3client, S3ClientConfigurarionProperties s3config) {
        this.s3client = s3client;
        this.s3config = s3config;
    }

    @GetMapping(path = "/{filekey}")
    public Mono<ResponseEntity<Flux<ByteBuffer>>> downloadFile(@PathVariable("filekey") String filekey) {

        GetObjectRequest request = GetObjectRequest.builder()
            .bucket(s3config.getBucket())
            .key(filekey)
            .build();

        return Mono.fromFuture(s3client.getObject(request, AsyncResponseTransformer.toPublisher()))
            .map(response -> {
                checkResult(response.response());
                String filename = getMetadataItem(response.response(), "filename", filekey);

                log.info("[I65] filename={}, length={}", filename, response.response()
                    .contentLength());

                return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, response.response()
                        .contentType())
                    .header(HttpHeaders.CONTENT_LENGTH, Long.toString(response.response()
                        .contentLength()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .body(Flux.from(response));
            });
    }

    /**
     * Lookup a metadata key in a case-insensitive way.
     * @param sdkResponse
     * @param key
     * @param defaultValue
     * @return
     */
    private String getMetadataItem(GetObjectResponse sdkResponse, String key, String defaultValue) {
        for (Entry<String, String> entry : sdkResponse.metadata()
            .entrySet()) {
            if (entry.getKey()
                .equalsIgnoreCase(key)) {
                return entry.getValue();
            }
        }
        return defaultValue;
    }

    // Helper used to check return codes from an API call
    private static void checkResult(GetObjectResponse response) {
        SdkHttpResponse sdkResponse = response.sdkHttpResponse();
        if (sdkResponse != null && sdkResponse.isSuccessful()) {
            return;
        }

        throw new DownloadFailedException(response);
    }

}
