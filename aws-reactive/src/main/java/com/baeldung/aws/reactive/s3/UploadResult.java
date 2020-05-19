package com.baeldung.aws.reactive.s3;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UploadResult {
    HttpStatus status;
    String[] keys;

    public UploadResult() {}
    
    public UploadResult(HttpStatus status, List<String> keys) {
        this.status = status;
        this.keys = keys == null ? new String[] {}: keys.toArray(new String[] {});

    }
}
