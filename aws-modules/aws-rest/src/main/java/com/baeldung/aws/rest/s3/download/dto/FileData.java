package com.baeldung.aws.rest.s3.download.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

@RequiredArgsConstructor
@Data
@Builder
public class FileData {
    private final byte[] fileContent;
    private final String contentType;
    private final String contentDisposition;
    private final GetObjectRequest request;
}
