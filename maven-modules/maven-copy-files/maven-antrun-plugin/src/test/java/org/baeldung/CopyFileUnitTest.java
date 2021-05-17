package org.baeldung;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class CopyFileUnitTest {

    @Test
    public void whenCopyingAFileFromSourceToDestination_thenFileShouldBeInDestination() {
        File destinationFile = new File("target/destination-folder/foo.txt");
        assertEquals(true, destinationFile.exists());
    }
}
