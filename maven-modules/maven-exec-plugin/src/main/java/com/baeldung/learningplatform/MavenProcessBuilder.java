package com.baeldung.learningplatform;

import java.io.IOException;
import java.nio.file.Path;

public class MavenProcessBuilder extends MavenExecutorAdapter {
    private static final ProcessBuilder PROCESS_BUILDER = new ProcessBuilder();

    protected int execute(Path projectFolder, String compileGoal) throws IOException, InterruptedException {
        Process process = PROCESS_BUILDER
          .command(MVN, USE_CUSTOM_POM, projectFolder.resolve(POM_XML).toString(), compileGoal)
          .start();
        return process.waitFor();
    }
}
