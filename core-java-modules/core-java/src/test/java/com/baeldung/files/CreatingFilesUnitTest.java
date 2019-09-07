import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class CreateFilesUnitTest {
    @Test(expected = IOException.class)
    public void whenCreatingAFileWithAbsolutePath_thenExceptionIsThrown() throws IOException {
        File fileWithAbsolutePath = new File("/myDirectory/testFile.txt");

        Files.touch(fileWithAbsolutePath);
    }

    @Test(expected = IOException.class)
    public void givenAnExistingDirectory_whenCreatingANewDirectoryAndFile_thenExceptionIsThrown() throws IOException {
        File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
        File fileWithRelativePath = new File(tempDirectory, "myDirectory/newFile.txt");

        Files.touch(fileWithRelativePath);
    }

    @Test(expected = IOException.class)
    public void whenCreatingAFileWithFileSeparator_thenPathIsCreated() throws IOException {
        File newFile = new File("src" + File.separator + "test" + File.separator + "resources" + File.separator + "newFile.txt");

        Files.touch(newFile);
    }
}
