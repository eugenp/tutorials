package com.hitotech.hexagonal.service;

import com.hitotech.hexagonal.repository.DocumentDatabaseRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class DocumentHexagonalService extends CommonHexagonalService {


    public DocumentHexagonalService(DocumentDatabaseRepository repository){
        super(repository);
    }

    @Override
    public String getMessage(Long id) {
        return this.getMessage(Long.valueOf(id * 10));
    }

    @Scheduled(fixedDelay = 10000)
    private void scheduledMessage() {
        String message = this.getMessage(Long.valueOf(100));
        System.out.println("scheduled " + message);
    }

}
