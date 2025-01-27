package com.baeldung.spring.pkl;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("pkl")
public class PklConfigUnitTest {
    final Logger logger = LoggerFactory.getLogger(PklConfigUnitTest.class);
    @Autowired
    private BaeldungService baeldungService;

    @Autowired
    private CustomBaeldungProperties customBaeldungProperties;

    @Test
    void test() {
        logger.info("Name: {}, Port: {}", baeldungService.getCustomBaeldungProperties().getName(),
            baeldungService.getCustomBaeldungProperties().getPort());
        logger.info("Name: {}, Port: {}", customBaeldungProperties.getName(),
            customBaeldungProperties.getPort());

        assertEquals(8080, baeldungService.getCustomBaeldungProperties().getPort());
        assertEquals(8080, customBaeldungProperties.getPort());
        assertEquals("PKL Language", baeldungService.getCustomBaeldungProperties().getName());
        assertEquals("PKL Language", customBaeldungProperties.getName());
    }

}
