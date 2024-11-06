package com.baeldung.kafka.batch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DataLakeService {
    private final Logger logger = LoggerFactory.getLogger(DataLakeService.class);
    public void save(List<String> messages) {
        logger.info("Transform and save the data into the data lake");
    }

}
