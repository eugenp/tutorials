package com.baeldung.beanInjectionTypesTest;

import com.baeldung.beaninjectiontypes.Config;
import com.baeldung.beaninjectiontypes.FileUploaderConstructorInjection;
import com.baeldung.beaninjectiontypes.FileUploaderSetterInjection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class BeanInjectionTypesTest {

    File file = null;

    @Before
    public void setup() throws IOException {
        char[] chars = new char[1025]; //1KByte

        File file = new File("testFile.txt");
        BufferedWriter outputWriter = new BufferedWriter(new FileWriter(file));
        outputWriter.write(chars);

        this.file = file;
    }

    @After
    public void cleanup() throws IOException {
        Files.deleteIfExists(file.toPath());
    }

    @Test(expected = RuntimeException.class)
    public void givenAutowired_WhenSetOnSetter_ThenDependencyValid() throws IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        FileUploaderSetterInjection setterInjectionBean = (FileUploaderSetterInjection) context.getBean("fileUploaderSetterInjection");
        //if everything works we expect a runtimeexception saying that the max file size is 1 KB
        setterInjectionBean.handleFileUpload(file);
    }

    @Test(expected = RuntimeException.class)
    public void givenAutowired_WhenSetOnConstructor_ThenDependencyValid() throws IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        FileUploaderConstructorInjection constructorInjectionBean = (FileUploaderConstructorInjection) context.getBean("fileUploaderConstructorInjection");
        //if everything works we expect a runtimeexception saying that the max file size is 1 KB
        constructorInjectionBean.handleFileUpload(file);
    }

}
