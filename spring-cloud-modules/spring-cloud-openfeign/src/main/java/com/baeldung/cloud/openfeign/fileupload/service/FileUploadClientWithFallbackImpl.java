package com.baeldung.cloud.openfeign.fileupload.service;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.baeldung.cloud.openfeign.exception.BadRequestException;
import com.baeldung.cloud.openfeign.exception.NotFoundException;

@Component
public class FileUploadClientWithFallbackImpl implements FileUploadClientWithFallBack {

    @Override
    public String fileUpload(MultipartFile file) {
        try {
            throw new NotFoundException("hi, something wrong");
        } catch (Exception ex) {

            if (ex instanceof BadRequestException) {
                return "Bad Request!!!";
            }
            if (ex instanceof NotFoundException) {
                return "Not Found!!!";
            }
            if (ex instanceof Exception) {
                return "Exception!!!";
            }
            return "Successfully Uploaded file!!!";
        }
    }

}
