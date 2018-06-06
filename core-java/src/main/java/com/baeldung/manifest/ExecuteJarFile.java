package com.baeldung.manifest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExecuteJarFile {

    private static final String DELIMITER = " ";
    private static final String WORK_PATH = "src/main/java/com/baeldung/manifest";
    
    private static final String COMPILE_COMMAND = "javac -d . AppExample.java";
    private static final String CREATE_JAR_WITHOUT_MF_ATT_COMMAND = "jar cvf example.jar com/baeldung/manifest/AppExample.class";
    private static final String CREATE_JAR_WITH_MF_ATT_COMMAND = "jar cvmf MANIFEST.MF example.jar com/baeldung/manifest/AppExample.class";
    private static final String EXECUTE_JAR_COMMAND = "java -jar example.jar";

    public static void main(String[] args) {
        System.out.println(executeJarWithoutManifestAttribute());
        System.out.println(executeJarWithManifestAttribute());
    }

    public static String executeJarWithoutManifestAttribute() {
        return executeJar(CREATE_JAR_WITHOUT_MF_ATT_COMMAND);
    }
    
    public static String executeJarWithManifestAttribute() {
        return executeJar(CREATE_JAR_WITH_MF_ATT_COMMAND);
    }
    
    private static String executeJar(String createJarCommand) {
        executeCommand(COMPILE_COMMAND);
        executeCommand(createJarCommand);
        return executeCommand(EXECUTE_JAR_COMMAND);
    }
    
    private static String executeCommand(String command) {
        String output = null;
        try {
            output = collectOutput(runProcess(command));           
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return output;
    }

    private static String collectOutput(Process process) throws IOException {
        StringBuffer output = new StringBuffer();
        BufferedReader outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        while ((line = outputReader.readLine()) != null) {
            output.append(line + "\n");
        }
        return output.toString();
    }

    private static Process runProcess(String command) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder(command.split(DELIMITER));
        builder.directory(new File(WORK_PATH).getAbsoluteFile()); 
        builder.redirectErrorStream(true);
        Process process = builder.start();
        process.waitFor();
        return process;
    }

}
