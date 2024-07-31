package com.baeldung.learningplatform;

import java.nio.file.Path;

public interface Maven {
    String POM_XML = "pom.xml";
    String COMPILE_GOAL = "compile";
    String USE_CUSTOM_POM = "-f";
    int OK = 0;
    String MVN = "mvn";

    void compile(Path projectFolder);
}
