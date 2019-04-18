package com.baeldung.resourcedirectory;

import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReadResourceDirectoryTest {

    @Test
    public void shouldReadResourceAbsolutePathWithFile() {
        String path = "src/test/resources";

        File file = new File(path);
        String absolutePath = file.getAbsolutePath();

        System.out.println(absolutePath);
    }

    @Test
    public void shouldReadResourceAbsolutePathWithResources() {
        String resourceName = "example_resource.txt";

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(resourceName).getFile());
        String absolutePath = file.getAbsolutePath();

        System.out.println(absolutePath);
    }

    @Test
    public void shouldReadResourceAbsolutePathWithPaths() {
        Path resourceDirectory = Paths.get("src","test","resources");

        String absolutePath = resourceDirectory.toFile().getAbsolutePath();

        System.out.println(absolutePath);
    }


}
