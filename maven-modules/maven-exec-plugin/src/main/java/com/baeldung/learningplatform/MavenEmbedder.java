package com.baeldung.learningplatform;

import java.nio.file.Path;
import org.apache.maven.cli.MavenCli;

public class MavenEmbedder implements Maven {

    public static final String MVN_HOME = "maven.multiModuleProjectDirectory";

    @Override
    public void compile(Path projectFolder) {
        MavenCli cli = new MavenCli();
        System.setProperty(MVN_HOME, projectFolder.toString());
        cli.doMain(new String[]{COMPILE_GOAL}, projectFolder.toString(), null, null);
    }
}
