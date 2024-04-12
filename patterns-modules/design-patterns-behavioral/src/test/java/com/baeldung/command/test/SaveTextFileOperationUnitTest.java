package com.baeldung.command.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.baeldung.command.command.SaveTextFileOperation;
import com.baeldung.command.command.TextFileOperation;
import com.baeldung.command.receiver.TextFile;

public class SaveTextFileOperationUnitTest {
    
    @Test
    public void givenSaveTextFileOperationIntance_whenCalledExecuteMethod_thenOneAssertion() {
        TextFileOperation openTextFileOperation = new SaveTextFileOperation(new TextFile("file1.txt"));
        assertThat(openTextFileOperation.execute()).isEqualTo("Saving file file1.txt");
    }
}
