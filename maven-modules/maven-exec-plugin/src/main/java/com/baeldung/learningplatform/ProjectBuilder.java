package com.baeldung.learningplatform;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.apache.maven.model.Build;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.xml.Xpp3Dom;

public class ProjectBuilder {

    private static final String PACKAGE = "package ";
    private static final String POM_XML = "pom.xml";
    private static final String SRC_TEST = "src/test/";
    private static final String SRC_MAIN_JAVA = "src/main/java/";
    private static final String PACKAGE_DELIMITER = ".";
    private static final String MAIN_JAVA = "Main.java";
    private final List<Dependency> dependencies = new ArrayList<>();
    private JavaVersion javaVersion = JavaVersion.JAVA_8;

    public ProjectBuilder addDependency(String groupId, String artifactId, String version) {
        Dependency dependency = new Dependency();
        dependency.setGroupId(groupId);
        dependency.setArtifactId(artifactId);
        dependency.setVersion(version);
        dependencies.add(dependency);
        return this;
    }

    public ProjectBuilder setJavaVersion(JavaVersion version) {
        this.javaVersion = version;
        return this;
    }

    public void build(String userName, Path projectPath, String packageName) throws IOException {
        Model model = new Model();
        configureModel(userName, model);
        dependencies.forEach(model::addDependency);
        Build build = configureJavaVersion();
        model.setBuild(build);
        MavenXpp3Writer writer = new MavenXpp3Writer();
        writer.write(new FileWriter(projectPath.resolve(POM_XML).toFile()), model);
        generateFolders(projectPath, SRC_TEST);

        Path generatedPackage = generateFolders(projectPath,
          SRC_MAIN_JAVA +
          packageName.replace(PACKAGE_DELIMITER, FileSystems.getDefault().getSeparator()));
        String generatedClass = generateMainClass(PACKAGE + packageName);
        Files.writeString(generatedPackage.resolve(MAIN_JAVA), generatedClass);
    }

    private static void configureModel(String userName, Model model) {
        model.setModelVersion("4.0.0");
        model.setArtifactId("com." + userName.toLowerCase());
        model.setGroupId("learning-project");
        model.setVersion("0.0.1-SNAPSHOT");
    }

    private static String generateMainClass(String packageName) {
        return packageName + ";\n" +
               "\n" +
               "public class Main {\n" +
               "    public static void main(String[] args){\n" +
               "      System.out.println(\"Hello World!\");\n" +
               "    }\n" +
               "}\n";
    }

    private static Path generateFolders(Path sourceFolder, String packageName) throws IOException {
        return Files.createDirectories(sourceFolder.resolve(packageName));
    }

    private Build configureJavaVersion() {
        Plugin plugin = new Plugin();
        plugin.setGroupId("org.apache.maven.plugins");
        plugin.setArtifactId("maven-compiler-plugin");
        plugin.setVersion("3.8.1");

        Xpp3Dom configuration = new Xpp3Dom("configuration");
        Xpp3Dom source = new Xpp3Dom("source");
        source.setValue(javaVersion.getVersion());
        Xpp3Dom target = new Xpp3Dom("target");
        target.setValue(javaVersion.getVersion());
        configuration.addChild(source);
        configuration.addChild(target);

        plugin.setConfiguration(configuration);

        Build build = new Build();
        build.addPlugin(plugin);
        return build;
    }
}
