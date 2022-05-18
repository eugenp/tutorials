package com.baeldung.simulation.touch.command;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Simulator {

    public static void touch(String path, String... args) throws IOException, ParseException {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
            if (args.length == 0) {
                return;
            }
        }
        long timeMillis = args.length < 2 ? System.currentTimeMillis() : new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").parse(args[1]).getTime();
        if (args.length > 0) {
            // change access time only
            if ("a".equals(args[0])) {
                FileTime accessFileTime = FileTime.fromMillis(timeMillis);
                Files.setAttribute(file.toPath(), "lastAccessTime", accessFileTime);
                return;
            }
            // change modification time only
            if ("m".equals(args[0])) {
                file.setLastModified(timeMillis);
                return;
            }
        }
        // other cases will change both
        FileTime accessFileTime = FileTime.fromMillis(timeMillis);
        Files.setAttribute(file.toPath(), "lastAccessTime", accessFileTime);
        file.setLastModified(timeMillis);
    }

    public static void touchWithApacheCommons(String path) throws IOException {
        FileUtils.touch(new File(path));
    }

    public static void main(String[] args) throws IOException, ParseException {
        touch("test.txt");
    }
}
