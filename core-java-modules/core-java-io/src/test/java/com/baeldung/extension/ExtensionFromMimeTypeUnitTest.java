package com.baeldung.extension;

import static org.junit.Assert.assertEquals;
import java.util.HashMap;
import java.util.Map;

import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.junit.Test;

import com.j256.simplemagic.ContentInfo;

public class ExtensionFromMimeTypeUnitTest {
    public static final String PNG_EXT = "image/png";
    @Test
    public void whenUsingTika_thenGetFileExtension() throws MimeTypeException {
        MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
        MimeType type = allTypes.forName(PNG_EXT);
        String extension = type.getExtension();
        assertEquals(".png", extension);
    }

    @Test
    public void  whenUsingJodd_thenGetFileExtension() {
        String fileExtension = jodd.net.MimeTypes.findExtensionsByMimeTypes(PNG_EXT,false)[0];
        assertEquals("png", fileExtension);
    }

    @Test
    public void whenUsingMimetypesFileTypeMap_thenGetFileExtension() {
        ContentInfo contentInfo = new ContentInfo("", PNG_EXT, "", true);
        String[] fileExtensions = contentInfo.getFileExtensions();
        assertEquals("png", fileExtensions[0]);
    }

    @Test
    public void whenUsingCustomLogic_thenGetFileExtension() {
        Map<String, String> mimeToExtensionMap = new HashMap<>();
        mimeToExtensionMap.put("application/pdf", "pdf");
        mimeToExtensionMap.put("image/jpeg", "jpg");
        mimeToExtensionMap.put("image/png", "png");
        String fileExtension = mimeToExtensionMap.get(PNG_EXT);
        if (fileExtension!= null)
            assertEquals("png", fileExtension);
    }
}
