import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class CreateFilesUnitTest {
    @Test(expected = IOException.class)
    public void whenCreatingAFileWithAbsolutePath_thenExceptionIsThrown() throws IOException {
        File fileWithAbsolutePath = new File("/myDirectory/testFile.txt");

        Files.touch(fileWithAbsolutePath);
    }

    @Test
    public void givenAnExistingDirectory_whenCreatingANewDirectoryAndFile_thenFileIsCreated() throws IOException {
        File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
        File fileWithRelativePath = new File(tempDirectory, "myDirectory/newFile.txt");

        fileWithRelativePath.mkdirs();
        Files.touch(fileWithRelativePath);

        assertTrue(fileWithRelativePath.exists());
    }

    @Test
    public void whenCreatingAFileWithFileSeparator_thenFileIsCreated() throws IOException {
        File newFile = new File("src" + File.separator + "test" + File.separator + "resources" + File.separator + "newFile.txt");

        newFile.mkdirs();
        Files.touch(newFile);

        assertTrue(newFile.exists());
    }
}
