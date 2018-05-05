package com.baeldung.pattern.command.test;

import com.baeldung.pattern.command.command.OpenTextFileOperation;
import com.baeldung.pattern.command.command.TextFileOperation;
import com.baeldung.pattern.command.receiver.TextFile;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class OpenTextFileOperationUnitTest {
    
    @Test
    public void givenOpenTextFileOperationIntance_whenCalledExecuteMethod_thenOneAssertion() {
        TextFileOperation openTextFileOperation = new OpenTextFileOperation(new TextFile("file1.txt"));
        assertThat(openTextFileOperation.execute()).isEqualTo("Opening file file1.txt");
    }
}
