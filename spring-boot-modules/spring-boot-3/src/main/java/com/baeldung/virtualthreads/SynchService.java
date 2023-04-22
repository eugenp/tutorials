package com.baeldung.virtualthreads;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class SynchService {

    private static final Logger LOG = LoggerFactory.getLogger(SynchService.class);

    public void synchMethod() throws IOException {
        LOG.info("hey, I'm doing something synch - Reading 10 bytes from a file: ");
        ClassPathResource resource = new ClassPathResource("test.txt");
        LOG.info(new String(resource.getInputStream().readNBytes(10)));
    }

}
