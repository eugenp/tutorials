package com.baeldung.aws.reactive.s3;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadResult {
	HttpStatus status = HttpStatus.CREATED;
	String[] key;
}
