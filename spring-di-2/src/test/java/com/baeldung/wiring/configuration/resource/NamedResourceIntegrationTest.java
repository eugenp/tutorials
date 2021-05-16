package com.baeldung.wiring.configuration.resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.wiring.configuration.ApplicationContextTestResourceNameType;

import javax.annotation.Resource;
import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,
  classes = ApplicationContextTestResourceNameType.class)
public class NamedResourceIntegrationTest {

    @Resource(name = "namedFile")
    private File testFile;

    @Test
    public void givenResourceAnnotation_WhenOnField_THEN_DEPENDENCY_Found() {
        assertNotNull(testFile);
        assertEquals("namedFile.txt", testFile.getName());
    }
}
