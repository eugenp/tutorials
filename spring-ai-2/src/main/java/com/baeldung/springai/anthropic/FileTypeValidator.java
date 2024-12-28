package com.baeldung.springai.anthropic;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FileTypeValidator implements ConstraintValidator<ValidFileType, MultipartFile> {

    private final List<String> ALLOWED_CONTENT_TYPES = List.of(
        "image/jpeg", "image/png", "image/gif", "image/webp", "application/pdf"
    );

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return true;
        }
        String contentType = file.getContentType().toLowerCase();
        return ALLOWED_CONTENT_TYPES.contains(contentType);
    }

}