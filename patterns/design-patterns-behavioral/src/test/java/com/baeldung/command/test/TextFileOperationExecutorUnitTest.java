package com.baeldung.command.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Function;

import org.junit.BeforeClass;
import org.junit.Test;

import com.baeldung.command.command.OpenTextFileOperation;
import com.baeldung.command.command.SaveTextFileOperation;
import com.baeldung.command.command.TextFileOperation;
import com.baeldung.command.invoker.TextFileOperationExecutor;
import com.baeldung.command.receiver.TextFile;

public class TextFileOperationExecutorUnitTest {
    
    private static TextFileOperationExecutor textFileOperationExecutor;
    
    
    @BeforeClass
    public static void setUpTextFileOperationExecutor() {
        textFileOperationExecutor = new TextFileOperationExecutor();
    }
    
    @Test
    public void givenTextFileOPerationExecutorInstance_whenCalledexecuteOperationWithOpenTextOperation_thenOneAssertion() {
        TextFileOperation textFileOperation = new OpenTextFileOperation(new TextFile("file1.txt"));
        assertThat(textFileOperationExecutor.executeOperation(textFileOperation)).isEqualTo("Opening file file1.txt");
    }
    
    @Test
    public void givenTextFileOPerationExecutorInstance_whenCalledexecuteOperationWithSaveTextOperation_thenOneAssertion() {
        TextFileOperation textFileOperation = new SaveTextFileOperation(new TextFile("file1.txt"));
        assertThat(textFileOperationExecutor.executeOperation(textFileOperation)).isEqualTo("Saving file file1.txt");
    }
    
    @Test
    public void givenTextFileOperationExecutorInstance_whenCalledexecuteOperationWithTextFileOpenLambda_thenOneAssertion() {
        assertThat(textFileOperationExecutor.executeOperation(() -> {return "Opening file file1.txt";})).isEqualTo("Opening file file1.txt");
    }
    
    @Test
    public void givenTextFileOperationExecutorInstance_whenCalledexecuteOperationWithTextFileSaveLambda_thenOneAssertion() {
        assertThat(textFileOperationExecutor.executeOperation(() -> {return "Saving file file1.txt";})).isEqualTo("Saving file file1.txt");
    }
    
    @Test
    public void givenTextFileOperationExecutorInstance_whenCalledexecuteOperationWithTextFileOpenMethodReferenceOfExistingObject_thenOneAssertion() {
        TextFile textFile = new TextFile("file1.txt");
        assertThat(textFileOperationExecutor.executeOperation(textFile::open)).isEqualTo("Opening file file1.txt");
    }
    
    @Test
    public void givenTextFileOperationExecutorInstance_whenCalledexecuteOperationWithTextFileSaveMethodReferenceOfExistingObject_thenOneAssertion() {
        TextFile textFile = new TextFile("file1.txt");
        assertThat(textFileOperationExecutor.executeOperation(textFile::save)).isEqualTo("Saving file file1.txt");
    }
    
    @Test
    public void givenOpenTextFileOperationExecuteMethodReference_whenCalledApplyMethod_thenOneAssertion() {
        Function<OpenTextFileOperation, String> executeMethodReference = OpenTextFileOperation::execute;
        assertThat(executeMethodReference.apply(new OpenTextFileOperation(new TextFile("file1.txt")))).isEqualTo("Opening file file1.txt"); 
    }
    
    @Test
    public void givenSaveTextFileOperationExecuteMethodReference_whenCalledApplyMethod_thenOneAssertion() {
        Function<SaveTextFileOperation, String> executeMethodReference = SaveTextFileOperation::execute;
        assertThat(executeMethodReference.apply(new SaveTextFileOperation(new TextFile("file1.txt")))).isEqualTo("Saving file file1.txt"); 
    }
    
    @Test
    public void givenOpenAndSaveTextFileOperationExecutorInstance_whenCalledExecuteOperationWithLambdaExpression_thenBothAssertion() {
        TextFileOperationExecutor textFileOperationExecutor = new TextFileOperationExecutor();
        assertThat(textFileOperationExecutor.executeOperation(() -> "Opening file file1.txt")).isEqualTo("Opening file file1.txt");
        assertThat(textFileOperationExecutor.executeOperation(() -> "Saving file file1.txt")).isEqualTo("Saving file file1.txt");
    }
}
