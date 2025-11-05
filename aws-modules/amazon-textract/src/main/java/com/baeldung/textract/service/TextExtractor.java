package com.baeldung.textract.service;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import com.baeldung.textract.validation.ValidFileType;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.textract.TextractClient;
import software.amazon.awssdk.services.textract.model.Block;
import software.amazon.awssdk.services.textract.model.BlockType;
import software.amazon.awssdk.services.textract.model.DetectDocumentTextResponse;

@Service
@Validated
public class TextExtractor {

    private final TextractClient textractClient;

    public TextExtractor(TextractClient textractClient) {
        this.textractClient = textractClient;
    }

    public String extract(@ValidFileType MultipartFile image) throws IOException {
        byte[] imageBytes = image.getBytes();
        DetectDocumentTextResponse response = textractClient.detectDocumentText(request -> request
            .document(document -> document
                .bytes(SdkBytes.fromByteArray(imageBytes))
                .build())
            .build());
        
        return transformTextDetectionResponse(response);
    }

    public String extract(String bucketName, String objectKey) {
        DetectDocumentTextResponse response = textractClient.detectDocumentText(request -> request
            .document(document -> document
                .s3Object(s3Object -> s3Object
                    .bucket(bucketName)
                    .name(objectKey)
                    .build())
                .build())
            .build());
        
        return transformTextDetectionResponse(response);
    }

    private String transformTextDetectionResponse(DetectDocumentTextResponse response) {
        return response.blocks()
            .stream()
            .filter(block -> block.blockType().equals(BlockType.LINE))
            .map(Block::text)
            .collect(Collectors.joining(" "));
    }

}
