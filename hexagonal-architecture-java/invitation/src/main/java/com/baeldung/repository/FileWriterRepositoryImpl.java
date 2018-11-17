package com.baeldung.repository;

import com.baeldung.domain.FileWriterResponse;
import org.springframework.stereotype.Repository;

@Repository
public class FileWriterRepositoryImpl implements FileWriterRepository {

    @Override
    public FileWriterResponse createInvitation(FileWriterResponse fileWriterResponse) {

        // TODO : Write invitation in a file
        return fileWriterResponse;
    }

    @Override
    public FileWriterResponse getInvitation(String eventId) {

        //TODO : Read invitation from a file. Creating the object and returning temporarily
        FileWriterResponse fileWriterResponse = new FileWriterResponse();
        fileWriterResponse.setDate("01/01/2019");
        fileWriterResponse.setFileMessage("You are cordially invited for the birthday party");
        return fileWriterResponse;
    }
}
