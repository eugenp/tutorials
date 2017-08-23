package com.baeldung.spring.bean.injection.service;

import com.baeldung.spring.bean.injection.AbstractApplicationXml;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class WriterServiceTest extends AbstractApplicationXml {

        @Autowired private WriterService writerService;

        @Test public void testWriterService() {
                assertEquals("test", writerService.write("test"));
        }

}