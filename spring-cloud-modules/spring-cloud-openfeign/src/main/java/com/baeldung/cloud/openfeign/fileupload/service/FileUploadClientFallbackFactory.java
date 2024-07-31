package com.baeldung.cloud.openfeign.fileupload.service;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.baeldung.cloud.openfeign.exception.BadRequestException;
import com.baeldung.cloud.openfeign.exception.NotFoundException;

@Component
public class FileUploadClientFallbackFactory implements FallbackFactory<FileUploadClientWithFallbackFactory> {
    @Override
    public FileUploadClientWithFallbackFactory create(Throwable cause) {
        return file -> {
            if (cause instanceof BadRequestException) {
                return "Bad Request!!!";
            }
            if (cause instanceof NotFoundException) {
                return "Not Found!!!";
            }
            if (cause instanceof Exception) {
                return "Exception!!!";
            }
            return "Successfully Uploaded file!!!";
        };
    }
}