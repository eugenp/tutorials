package com.baeldung.learningplatform;

import java.io.IOException;
import java.nio.file.Path;

public class MavenProcessBuilder implements Maven {

    private static final ProcessBuilder PROCESS_BUILDER = new ProcessBuilder();

    @Override
    public void compile(Path projectFolder) {
        int exitCode;
        try {
            Process process = PROCESS_BUILDER
              .command(MVN, USE_CUSTOM_POM, projectFolder.resolve(POM_XML).toString(), COMPILE_GOAL)
              .start();
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
