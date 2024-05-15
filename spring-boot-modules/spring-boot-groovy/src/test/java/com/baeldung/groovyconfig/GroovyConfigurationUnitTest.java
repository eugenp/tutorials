package com.baeldung.groovyconfig;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;
import org.springframework.context.support.GenericGroovyApplicationContext;

import com.baeldung.groovyconfig.BandsBean;
import com.baeldung.groovyconfig.JavaPersonBean;

public class GroovyConfigurationUnitTest {
    
    private static final String FILE_NAME = "GroovyBeanConfig.groovy";
    private static final String FILE_PATH = "src/main/java/com/baeldung/groovyconfig/";

    @Test
    public void whenGroovyConfig_thenCorrectPerson() throws Exception {

        GenericGroovyApplicationContext ctx = new GenericGroovyApplicationContext();
        ctx.load("file:" + getPathPart() + FILE_NAME);
        ctx.refresh();

        JavaPersonBean j = ctx.getBean(JavaPersonBean.class);

        assertEquals("32", j.getAge());
        assertEquals("blue", j.getEyesColor());
        assertEquals("black", j.getHairColor());
    }

    @Test
    public void whenGroovyConfig_thenCorrectListLength() throws Exception {
        
        GenericGroovyApplicationContext ctx = new GenericGroovyApplicationContext();
        ctx.load("file:" + getPathPart() + FILE_NAME);
        ctx.refresh();

        BandsBean bb = ctx.getBean(BandsBean.class);

        assertEquals(3, bb.getBandsList()
            .size());
    }

    private String getPathPart() {
        String pathPart = new File(".").getAbsolutePath();
        pathPart = pathPart.replace(".", "");
        pathPart = pathPart.replace("\\", "/");
        pathPart = pathPart + FILE_PATH;

        return pathPart;
    }
}
