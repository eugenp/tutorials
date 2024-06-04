package com.baeldung.dirpermissions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;

public class CheckWritePermission {

    public boolean usingJavaIO(String path){
        File dir = new File(path);

        return dir.exists() && dir.canWrite();
    }

    public boolean usingJavaNIOWithFilesPackage(String path){
        Path dirPath = Paths.get(path);
        return Files.isDirectory(dirPath) && Files.isWritable(dirPath);
    }

    public boolean usingJavaNIOWithPOSIX(String path) throws IOException {
        Path dirPath = Paths.get(path);
        Set<PosixFilePermission> permissions = Files.getPosixFilePermissions(dirPath);
        return permissions.contains(PosixFilePermission.OWNER_WRITE);
    }
}
