package com.baeldung.s3;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AWSS3ObjectIntegrationTest {

    private static final String BUCKET = "my-awesome-bucket";
    private static final String KEY = "my-awesome-key";
    private AWSS3ObjectUtils s3ObjectUtils;

    @Before
    public void setUp() {
        s3ObjectUtils = mock(AWSS3ObjectUtils.class);
    }

    @Test
    public void whenVerifyIfObjectExistByDefaultMethod_thenCorrect() {
        s3ObjectUtils.doesObjectExistByDefaultMethod(BUCKET, KEY);
        verify(s3ObjectUtils).doesObjectExistByDefaultMethod(BUCKET, KEY);
    }

    @Test
    public void whenVerifyIfObjectExistByListObjects_thenCorrect() {
        s3ObjectUtils.doesObjectExistByListObjects(BUCKET, KEY);
        verify(s3ObjectUtils).doesObjectExistByListObjects(BUCKET, KEY);
    }

    @Test
    public void whenVerifyIfObjectExistByMetaData_thenCorrect() {
        s3ObjectUtils.doesObjectExistByMetaData(BUCKET, KEY);
        verify(s3ObjectUtils).doesObjectExistByMetaData(BUCKET, KEY);
    }
}
