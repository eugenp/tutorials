package com.baeldung.textract.validation;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TextractFileValidator implements ConstraintValidator<ValidFileType, MultipartFile> {

    private static final List<String> VALID_CONTENT_TYPES = List.of(
        "image/png",
        "image/jpeg",
        "image/tiff",
        "application/pdf");

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return false;
        }
        return VALID_CONTENT_TYPES.contains(file.getContentType());
    }

}