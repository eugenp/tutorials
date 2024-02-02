package com.baeldung.learningplatform;

import java.nio.file.Path;
import java.util.Collections;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;

public class MavenInvoker implements Maven {

    @Override
    public void compile(Path projectFolder) {
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(projectFolder.resolve(POM_XML).toFile());
        request.setGoals(Collections.singletonList(Maven.COMPILE_GOAL));
        Invoker invoker = new DefaultInvoker();
        try {
            InvocationResult result = invoker.execute(request);
            if (result.getExitCode() != 0) {
                throw new MavenCompilationException("Build failed", result.getExecutionException());
            }
        } catch (MavenInvocationException e) {
            throw new MavenCompilationException("Exception during Maven invocation", e);
        }

    }
}
