package com.baeldung.command.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.baeldung.command.command.OpenTextFileOperation;
import com.baeldung.command.command.TextFileOperation;
import com.baeldung.command.receiver.TextFile;

public class OpenTextFileOperationUnitTest {
    
    @Test
    public void givenOpenTextFileOperationIntance_whenCalledExecuteMethod_thenOneAssertion() {
        TextFileOperation openTextFileOperation = new OpenTextFileOperation(new TextFile("file1.txt"));
        assertThat(openTextFileOperation.execute()).isEqualTo("Opening file file1.txt");
    }
}
