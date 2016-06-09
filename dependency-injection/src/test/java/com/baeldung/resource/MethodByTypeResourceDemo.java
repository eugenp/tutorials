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
public class MethodByTypeResourceDemo {

    private File defaultFile;

    @Resource
    protected void setDefaultFile(File defaultFile) {
        this.defaultFile = defaultFile;
    }

    @Test
    public void defaultFile_MUST_BE_INJECTED_Correctly() {
        assertNotNull(defaultFile);
    }
}
