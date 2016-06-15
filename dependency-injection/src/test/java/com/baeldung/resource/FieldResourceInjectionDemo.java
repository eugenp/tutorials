package com.baeldung.resource;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"/applicationContextTest-@Resource-NameType.xml"})
public class FieldResourceInjectionDemo {

    @Resource(name="namedFile")
    private File defaultFile;

    @Test
    public void plainResourceAnnotation_MUST_FIND_DefaultFile() {
        assertNotNull(defaultFile);
    }
}
