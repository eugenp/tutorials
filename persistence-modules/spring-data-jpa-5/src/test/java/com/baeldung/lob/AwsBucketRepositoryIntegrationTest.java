package com.baeldung.lob;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.amazonaws.services.s3.model.CompleteMultipartUploadResult;

@Disabled("Disabled because we need to provide the S3 bucket crendentials to work")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AwsBucketConfiguration.class)
@ContextConfiguration(classes = AwsBucketRepository.class)
class AwsBucketRepositoryIntegrationTest {

    @Autowired
    AwsBucketRepository bucket;

    @Test
    void givenFile_whenUploadMultpartFile_shallSendTheCompleteFilePartByPart() throws IOException {
        String fileName = "cat/wise-cat.jpeg";
        byte[] fileBytes = ResourceReader.readResourceAsBytes(fileName);
        MockMultipartFile multipartFile = new MockMultipartFile(fileName, fileBytes);

        CompleteMultipartUploadResult uploadResult = bucket.uploadMultipartFile(fileName, multipartFile);

        assertThat(uploadResult.getETag())
            .isNotNull();
    }

}
