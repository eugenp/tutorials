package com.baeldung.extension;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.tika.mime.MimeTypeException;

import org.junit.Test;

import com.j256.simplemagic.ContentInfo;

public class ExtensionFromMimeTypeUnitTest {
    private static final String IMG_MIME_TYPE = "image/jpeg";
    @Test
    public void whenUsingTika_thenGetFileExtension() throws MimeTypeException {
        List<String> SUPPORTED_EXTENSIONS = Arrays.asList(".jpg", ".jpeg", ".jpe", ".jif", ".jfif", ".jfi");
        org.apache.tika.mime.MimeTypes allTypes = org.apache.tika.mime.MimeTypes.getDefaultMimeTypes();
        org.apache.tika.mime.MimeType type = allTypes.forName(IMG_MIME_TYPE);
        String extension = type.getExtension();
        assertEquals(".jpg", extension);
        List<String> supportedExtensions = type.getExtensions();
        assertEquals(SUPPORTED_EXTENSIONS, supportedExtensions);
    }

    @Test
    public void  whenUsingJodd_thenGetFileExtension() {
        String[] supportedExtensions = {"jpeg","jpg","jpe"};
        String[] extensionsByMimeTypes = jodd.net.MimeTypes.findExtensionsByMimeTypes(IMG_MIME_TYPE, false);
        assertArrayEquals(supportedExtensions, extensionsByMimeTypes);
    }

    @Test
    public void whenUsingMimetypesFileTypeMap_thenGetFileExtension() {
        String[] supportedExtensions = {"jpeg","jpg","jpe"};
        ContentInfo contentInfo = new ContentInfo("", IMG_MIME_TYPE, "", true);
        String[] fileExtensions = contentInfo.getFileExtensions();
        assertArrayEquals(supportedExtensions, fileExtensions);
    }

    @Test
    public void whenUsingCustomLogic_thenGetFileExtension() {
        Map<String, Set<String>> mimeExtensionsMap = new HashMap<>();
        Set<String> supportedExtensions = new HashSet<>(Arrays.asList(".jpeg",".jpg",".jpe"));
        addMimeExtensions(mimeExtensionsMap, "image/jpeg", ".jpeg");
        addMimeExtensions(mimeExtensionsMap, "image/jpeg", ".jpg");
        addMimeExtensions(mimeExtensionsMap, "image/jpeg", ".jpe");

        String mimeTypeToLookup = "image/jpeg";
        Set<String> extensions = mimeExtensionsMap.get(mimeTypeToLookup);
        assertEquals(supportedExtensions, extensions);
    }

    private void addMimeExtensions(Map<String, Set<String>> map, String mimeType, String extension) {
        map.computeIfAbsent(mimeType, k -> new HashSet<>()).add(extension);
    }
}
