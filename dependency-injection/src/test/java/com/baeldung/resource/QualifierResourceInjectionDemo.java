package com.baeldung.resource;
import static org.junit.Assert.assertEquals;
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
		"/applicationContextTest-Resource-Qualifier.xml"})
public class QualifierResourceInjectionDemo {

    @Resource
    private File defaultFile;
	
    @Resource
    @Qualifier("namedFile")
    private File namedFile;

    @Test
    public void given_ResourceAnnotation_WhenField_THEN_DependencyValid(){
        assertNotNull(defaultFile);
        assertEquals("defaultFile.txt", defaultFile.getName());
    }

    @Test
    public void given_ResourceQualifier_WhenField_THEN_DependencyValid(){
        assertNotNull(namedFile);
        assertEquals("namedFile.txt", namedFile.getName());
    }
}
