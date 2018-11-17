package com.baeldung.repository;

import com.baeldung.domain.FileWriterResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class FileWriterRepositoryImplTest {

    @InjectMocks
    FileWriterRepositoryImpl fileWriterRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenFileResponseWhenRepositoryCallThenReturnCreateAndReturnFileResponse() {

        FileWriterResponse fileWriterResponse = new FileWriterResponse();
        fileWriterResponse.setDate("01/01/2019");
        fileWriterResponse.setFileMessage("You are cordially invited for the birthday party");

        FileWriterResponse createdFileResponse = fileWriterRepository.createInvitation(fileWriterResponse);
        Assert.assertNotNull(createdFileResponse);
        Assert.assertEquals("01/01/2019", createdFileResponse.getDate());

    }

    @Test
    public void givenFileResponseWhenRepositoryCallThenGetFileResponse() {
        String eventId = "1";
        FileWriterResponse fileWriterResponse = fileWriterRepository.getInvitation(eventId);
        Assert.assertNotNull(fileWriterResponse);
        Assert.assertEquals("01/01/2019", fileWriterResponse.getDate());

    }
}
