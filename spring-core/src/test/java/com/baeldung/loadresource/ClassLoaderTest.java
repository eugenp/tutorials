package com.baeldung.loadresource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = TestConfig.class)
public class ClassLoaderTest {

    @Autowired
    ClassLoaderSample classLoaderSample;

    @Autowired
    ResourceLoaderSample resourceLoaderSample;

    @Test
    public void whenUsingClassLoader() {
        String result = classLoaderSample.loadFile("resource_sample.txt");
        Assert.assertNotEquals("", result);
    }

    @Test
    public void whenUsingClassLoaderWithSubdirectory() {
        String result = classLoaderSample.loadFile("resource_dir/another_resource.txt");
        Assert.assertNotEquals("", result);
    }

    @Test
    public void whenUsingResourceLoader() {
        String result = resourceLoaderSample.loadFile("resource_sample.txt");
        Assert.assertNotEquals("", result);
    }

    @Test
    public void whenUsingResourceLoaderWithSubdirectory() {
        String result = resourceLoaderSample.loadFile("resource_dir/another_resource.txt");
        System.out.println(result);
        Assert.assertNotEquals("", result);
    }

}
