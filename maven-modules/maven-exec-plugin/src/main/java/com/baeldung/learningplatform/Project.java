package com.baeldung.learningplatform;

import java.nio.file.Path;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;

public class Project {

    private final Path projectPath;
    private final Model model;
    public Project(Path projectPath) {
        this.projectPath = projectPath;
        this.model = new Model();
    }

    public void addDependency(String groupId, String artifactId, String version) {
        Dependency dependency = new Dependency();
        dependency.setGroupId(groupId);
        dependency.setArtifactId(artifactId);
        dependency.setVersion(version);
        model.addDependency(dependency);
    }
}
