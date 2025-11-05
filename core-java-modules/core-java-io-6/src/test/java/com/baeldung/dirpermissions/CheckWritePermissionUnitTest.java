package com.baeldung.dirpermissions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CheckWritePermissionUnitTest {

    String readDirPath = "src/test/resources/dir1/";
    String writeDirPath = "src/test/resources/dir2/";

    @BeforeEach
     void create() throws IOException {
        Files.createDirectory(Paths.get(writeDirPath));
        Set<PosixFilePermission> permissions = new HashSet<>();
        permissions.add(PosixFilePermission.OWNER_WRITE);
        Files.setPosixFilePermissions(Paths.get(writeDirPath), permissions);

        Files.createDirectory(Paths.get(readDirPath));
        permissions = new HashSet<>();
        permissions.add(PosixFilePermission.OWNER_READ);
        Files.setPosixFilePermissions(Paths.get(readDirPath), permissions);
    }

    @AfterEach
     void destroy() throws IOException {
        Files.delete(Paths.get(readDirPath));
        Files.delete(Paths.get(writeDirPath));
    }

    @Test
    void givenDirectory_whenUsingJavaIO_thenReturnsPermission(){
        CheckWritePermission checkWritePermission = new CheckWritePermission();
        assertTrue(checkWritePermission.usingJavaIO(writeDirPath));
        assertFalse(checkWritePermission.usingJavaIO(readDirPath));
    }

    @Test
    void givenDirectory_whenUsingJavaNIOWithFilesPackage_thenReturnsPermission(){
        CheckWritePermission checkWritePermission = new CheckWritePermission();
        assertTrue(checkWritePermission.usingJavaNIOWithFilesPackage(writeDirPath));
        assertFalse(checkWritePermission.usingJavaNIOWithFilesPackage(readDirPath));
    }

    @Test
    void givenDirectory_whenUsingJavaNIOWithPOSIX_thenReturnsPermission() throws IOException {
        CheckWritePermission checkWritePermission = new CheckWritePermission();
        assertTrue(checkWritePermission.usingJavaNIOWithPOSIX(writeDirPath));
        assertFalse(checkWritePermission.usingJavaNIOWithPOSIX(readDirPath));
    }
}
