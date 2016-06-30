package com.baeldung.resource;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"/applicationContextTest-@Resource-Qualifier.xml"})
public class QualifierResourceInjectionDemo {

    @Resource
    private File defaultFile;
	
    @Resource
    @Qualifier("namedFile")
    private File namedFile;

    @Test
    public void defaultFile_MUST_BE_Valid() {
        assertNotNull(defaultFile);
    }

    @Test
    public void namedFile_MUST_BE_Valid() {
        assertNotNull(namedFile);
    }
}
