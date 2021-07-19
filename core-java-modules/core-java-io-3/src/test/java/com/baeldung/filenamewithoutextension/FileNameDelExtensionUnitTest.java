package com.baeldung.filenamewithoutextension;

import com.google.common.io.Files;
import org.apache.commons.io.FilenameUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class FileNameDelExtensionUnitTest {

    @Test
    public void givenDotFileWithoutExt_whenCallGuavaMethod_thenCannotGetDesiredResult() {
        //negative assertion
        assertNotEquals(".baeldung", Files.getNameWithoutExtension(".baeldung"));
    }

    @Test
    public void givenFileWithoutMultipleExt_whenCallGuavaMethod_thenCannotRemoveAllExtensions() {
        //negative assertion
        assertNotEquals("baeldung", Files.getNameWithoutExtension("baeldung.tar.gz"));
    }

    @Test
    public void givenDotFileWithoutExt_whenCallApacheCommonsMethod_thenCannotGetDesiredResult() {
        //negative assertion
        assertNotEquals(".baeldung", FilenameUtils.removeExtension(".baeldung"));
    }

    @Test
    public void givenFileWithoutMultipleExt_whenCallApacheCommonsMethod_thenCannotRemoveAllExtensions() {
        //negative assertion
        assertNotEquals("baeldung", FilenameUtils.removeExtension("baeldung.tar.gz"));
    }

    @Test
    public void givenFilenameNoExt_whenCallFilenameUtilMethod_thenGetExpectedFilename() {
        assertEquals("baeldung", MyFilenameUtil.removeFileExtension("baeldung", true));
        assertEquals("baeldung", MyFilenameUtil.removeFileExtension("baeldung", false));
    }

    @Test
    public void givenSingleExt_whenCallFilenameUtilMethod_thenGetExpectedFilename() {
        assertEquals("baeldung", MyFilenameUtil.removeFileExtension("baeldung.txt", true));
        assertEquals("baeldung", MyFilenameUtil.removeFileExtension("baeldung.txt", false));
    }


    @Test
    public void givenDotFile_whenCallFilenameUtilMethod_thenGetExpectedFilename() {
        assertEquals(".baeldung", MyFilenameUtil.removeFileExtension(".baeldung", true));
        assertEquals(".baeldung", MyFilenameUtil.removeFileExtension(".baeldung", false));
    }

    @Test
    public void givenDotFileWithExt_whenCallFilenameUtilMethod_thenGetExpectedFilename() {
        assertEquals(".baeldung", MyFilenameUtil.removeFileExtension(".baeldung.conf", true));
        assertEquals(".baeldung", MyFilenameUtil.removeFileExtension(".baeldung.conf", false));
    }

    @Test
    public void givenDoubleExt_whenCallFilenameUtilMethod_thenGetExpectedFilename() {
        assertEquals("baeldung", MyFilenameUtil.removeFileExtension("baeldung.tar.gz", true));
        assertEquals("baeldung.tar", MyFilenameUtil.removeFileExtension("baeldung.tar.gz", false));
    }

    @Test
    public void givenDotFileWithDoubleExt_whenCallFilenameUtilMethod_thenGetExpectedFilename() {
        assertEquals(".baeldung", MyFilenameUtil.removeFileExtension(".baeldung.conf.bak", true));
        assertEquals(".baeldung.conf", MyFilenameUtil.removeFileExtension(".baeldung.conf.bak", false));
    }
}
