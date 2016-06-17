package com.baeldung.resource;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"/applicationContextTest-@Resource-NameType.xml"})
public class NamedResourceTest {

    @Resource(name="namedFile")
    private File testFile;

    @Test
    public void namedResource_MUST_FIND_SPECIFIED_File() {
        assertNotNull(testFile);
        assertTrue(testFile.getName().equals("namedFile.txt"));
    }
}
