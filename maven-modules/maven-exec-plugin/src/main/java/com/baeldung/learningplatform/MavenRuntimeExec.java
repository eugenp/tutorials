package com.baeldung.learningplatform;

import java.io.IOException;
import java.nio.file.Path;

public class MavenRuntimeExec implements Maven {

    public static final String MVN_COMPILE = "mvn -f %s compile";

    @Override
    public void compile(Path projectFolder) {
        int exitCode;
        try {
            String[] arguments = {MVN, USE_CUSTOM_POM, projectFolder.resolve(POM_XML).toString(), COMPILE_GOAL};
            Process process = Runtime.getRuntime().exec(arguments);
            exitCode = process.waitFor();
        } catch (InterruptedException e) {
            throw new MavenCompilationException("Interrupted during compilation", e);
        } catch (IOException e) {
            throw new MavenCompilationException("Incorrect execution", e);
        }
        if (exitCode != OK) {
            throw new MavenCompilationException("Failure during compilation: " + exitCode);
        }
    }
}
