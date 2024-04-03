package com.baeldung.learningplatform;

import java.nio.file.Path;
import java.nio.file.Paths;

public interface ProjectsPaths {

    public static final Path PROJECT_DIR = Paths.get("src/test/resources/learning-project/");
    public static final Path PROJECT_POM_XML = PROJECT_DIR.resolve("pom.xml");
    public static final Path PROJECT_TARGET = PROJECT_DIR.resolve("target");
}
