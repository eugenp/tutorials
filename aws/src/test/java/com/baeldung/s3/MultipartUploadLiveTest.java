package com.baeldung.s3;

import com.amazonaws.event.ProgressListener;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MultipartUploadLiveTest {

    private static final String BUCKET_NAME = "bucket_name";
    private static final String KEY_NAME = "picture.jpg";

    private AmazonS3 amazonS3;
    private TransferManager tm;
    private ProgressListener progressListener;

    @Before
    public void setup() {
        amazonS3 = mock(AmazonS3.class);
        tm = TransferManagerBuilder
                .standard()
                .withS3Client(amazonS3)
                .withMultipartUploadThreshold((long) (5 * 1024 * 1025))
                .withExecutorFactory(() -> Executors.newFixedThreadPool(5))
                .build();
        progressListener =
                progressEvent -> System.out.println("Transferred bytes: " + progressEvent.getBytesTransferred());
    }

    @Test
    public void whenUploadingFileWithTransferManager_thenVerifyUploadRequested() {
        File file = mock(File.class);
        PutObjectResult s3Result = mock(PutObjectResult.class);

        when(amazonS3.putObject(anyString(), anyString(), (File) any())).thenReturn(s3Result);
        when(file.getName()).thenReturn(KEY_NAME);

        PutObjectRequest request = new PutObjectRequest(BUCKET_NAME, KEY_NAME, file);
        request.setGeneralProgressListener(progressListener);

        Upload upload = tm.upload(request);

        assertThat(upload).isNotNull();
        verify(amazonS3).putObject(request);
    }
}
