package com.baeldung.aws.rest.s3.download.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@Data
@RequiredArgsConstructor
public class FileDownloadResponse {
    private final byte[] fileContent;
    private final String originalFilename;
    private final String contentType;
}
