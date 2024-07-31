package com.baeldung.encoding.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HttpBinServiceLiveTest {
    @Autowired
    private HttpBinService httpBinService;

    @Test
    void givenWithoutPlusSign_whenGet_thenSameValueReturned() throws JsonProcessingException {
        String parameterWithoutPlusSign = "springboot";
        String responseWithoutPlusSign = httpBinService.get(parameterWithoutPlusSign);
        assertEquals(parameterWithoutPlusSign, responseWithoutPlusSign);
    }

    @Test
    void givenWithPlusSign_whenGet_thenSameValueReturned() throws JsonProcessingException {
        String parameterWithPlusSign = "spring+boot";
        String responseWithPlusSign = httpBinService.get(parameterWithPlusSign);
        assertEquals(parameterWithPlusSign, responseWithPlusSign);
    }
}