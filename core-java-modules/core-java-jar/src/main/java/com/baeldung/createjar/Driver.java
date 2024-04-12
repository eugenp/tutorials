package com.baeldung.createjar;

import java.io.File;
import java.io.IOException;
import java.util.jar.*;
import com.baeldung.createjar.JarTool;

public class Driver {

    public static void main(String[] args) throws IOException {
        JarTool tool = new JarTool();
        tool.startManifest();
        tool.addToManifest("Main-Class", "com.baeldung.createjar.HelloWorld");
        JarOutputStream target = tool.openJar("HelloWorld.jar");

        tool.addFile(target, System.getProperty("user.dir") + "\\src\\main\\java", System.getProperty("user.dir") + "\\src\\main\\java\\com\\baeldung\\createjar\\HelloWorld.class");
        target.close();
    }
}