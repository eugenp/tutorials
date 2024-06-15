package com.baeldung.aws.rest.s3.download.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class S3ObjectRequest {
    private final String bucketName;
    private final String objectKey;
}

