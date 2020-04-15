package com.baeldung.jenkins.plugins;

import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.tasks.BuildWrapper;
import hudson.tasks.BuildWrapperDescriptor;
import org.kohsuke.stapler.DataBoundConstructor;

import javax.annotation.Nonnull;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Stack;

public class ProjectStatsBuildWrapper extends BuildWrapper {

    private static final String REPORT_TEMPLATE_PATH = "/stats.html";
    private static final String PROJECT_NAME_VAR = "$PROJECT_NAME$";
    private static final String CLASSES_NUMBER_VAR = "$CLASSES_NUMBER$";
    private static final String LINES_NUMBER_VAR = "$LINES_NUMBER$";

    @DataBoundConstructor
    public ProjectStatsBuildWrapper() {
    }

    @Override
    public Environment setUp(AbstractBuild build, final Launcher launcher, BuildListener listener) {
        return new Environment() {
            @Override
            public boolean tearDown(AbstractBuild build, BuildListener listener)
              throws IOException, InterruptedException
            {
                ProjectStats stats = buildStats(build.getWorkspace());
                String report = generateReport(build.getProject().getDisplayName(), stats);
                File artifactsDir = build.getArtifactsDir();
                if (!artifactsDir.isDirectory()) {
                    boolean success = artifactsDir.mkdirs();
                    if (!success) {
                        listener.getLogger().println("Can't create artifacts directory at "
                          + artifactsDir.getAbsolutePath());
                    }
                }
                String path = artifactsDir.getCanonicalPath() + REPORT_TEMPLATE_PATH;
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path),
                  StandardCharsets.UTF_8))) {
                    writer.write(report);
                }
                return super.tearDown(build, listener);
            }
        };
    }

    private static ProjectStats buildStats(FilePath root) throws IOException, InterruptedException {
        int classesNumber = 0;
        int linesNumber = 0;
        Stack<FilePath> toProcess = new Stack<>();
        toProcess.push(root);
        while (!toProcess.isEmpty()) {
            FilePath path = toProcess.pop();
            if (path.isDirectory()) {
                toProcess.addAll(path.list());
            } else if (path.getName().endsWith(".java")) {
                classesNumber++;
                linesNumber += countLines(path);
            }
        }
        return new ProjectStats(classesNumber, linesNumber);
    }

    private static int countLines(FilePath path) throws IOException, InterruptedException {
        byte[] buffer = new byte[1024];
        int result = 1;
        try (InputStream in = path.read()) {
            while (true) {
                int read = in.read(buffer);
                if (read < 0) {
                    return result;
                }
                for (int i = 0; i < read; i++) {
                    if (buffer[i] == '\n') {
                        result++;
                    }
                }
            }
        }
    }

    private static String generateReport(String projectName, ProjectStats stats) throws IOException {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        try (InputStream in = ProjectStatsBuildWrapper.class.getResourceAsStream(REPORT_TEMPLATE_PATH)) {
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) >= 0) {
                bOut.write(buffer, 0, read);
            }
        }
        String content = new String(bOut.toByteArray(), StandardCharsets.UTF_8);
        content = content.replace(PROJECT_NAME_VAR, projectName);
        content = content.replace(CLASSES_NUMBER_VAR, String.valueOf(stats.getClassesNumber()));
        content = content.replace(LINES_NUMBER_VAR, String.valueOf(stats.getLinesNumber()));
        return content;
    }

    @Extension
    public static final class DescriptorImpl extends BuildWrapperDescriptor {

        @Override
        public boolean isApplicable(AbstractProject<?, ?> item) {
            return true;
        }

        @Nonnull
        @Override
        public String getDisplayName() {
            return "Construct project stats during build";
        }

    }

}
