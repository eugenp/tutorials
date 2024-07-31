package com.baeldung.extension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.tika.mime.MimeTypeException;
import com.j256.simplemagic.ContentType;
import org.junit.Test;

public class ExtensionFromMimeTypeUnitTest {
    private static final String IMAGE_JPEG_MIME_TYPE = "image/jpeg";
    @Test
    public void whenUsingTika_thenGetFileExtension() throws MimeTypeException {
        List<String> expectedExtensions = Arrays.asList(".jpg", ".jpeg", ".jpe", ".jif", ".jfif", ".jfi");
        org.apache.tika.mime.MimeTypes allTypes = org.apache.tika.mime.MimeTypes.getDefaultMimeTypes();
        org.apache.tika.mime.MimeType type = allTypes.forName(IMAGE_JPEG_MIME_TYPE);
        String primaryExtension = type.getExtension();
        assertEquals(".jpg", primaryExtension);
        List<String> detectedExtensions = type.getExtensions();
        assertThat(detectedExtensions).containsExactlyElementsOf(expectedExtensions);
    }

    @Test
    public void  whenUsingJodd_thenGetFileExtension() {
        List<String> expectedExtensions = Arrays.asList("jpeg", "jpg", "jpe");
        String[] detectedExtensions = jodd.net.MimeTypes.findExtensionsByMimeTypes(IMAGE_JPEG_MIME_TYPE, false);
        assertThat(detectedExtensions).containsExactlyElementsOf(expectedExtensions);
    }

    @Test
    public void whenUsingSimpleMagic_thenGetFileExtension() {
        List<String> expectedExtensions = Arrays.asList("jpeg", "jpg", "jpe");
        String[] detectedExtensions = ContentType.fromMimeType(IMAGE_JPEG_MIME_TYPE).getFileExtensions();
        assertThat(detectedExtensions).containsExactlyElementsOf(expectedExtensions);
    }

    @Test
    public void whenUsingCustomMap_thenGetFileExtension() {
        Map<String, Set<String>> mimeExtensionsMap = new HashMap<>();
        List<String> expectedExtensions = Arrays.asList(".jpg", ".jpe", ".jpeg");
        addMimeExtensions(mimeExtensionsMap, "image/jpeg", ".jpg");
        addMimeExtensions(mimeExtensionsMap, "image/jpeg", ".jpe");
        addMimeExtensions(mimeExtensionsMap, "image/jpeg", ".jpeg");

        Set<String> detectedExtensions = mimeExtensionsMap.get(IMAGE_JPEG_MIME_TYPE);
        assertThat(detectedExtensions).containsExactlyElementsOf(expectedExtensions);
    }

    private void addMimeExtensions(Map<String, Set<String>> map, String mimeType, String extension) {
        map.computeIfAbsent(mimeType, k -> new HashSet<>()).add(extension);
    }
}
