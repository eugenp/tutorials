package com.baeldung.spring.bean.injection.service;

import com.baeldung.spring.bean.injection.AbstractApplicationXml;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class ReaderServiceTest extends AbstractApplicationXml {

        @Autowired private ReaderService readerService;

        @Test public void testReaderService() {
                assertEquals("Reached read value", readerService.read());
        }

}