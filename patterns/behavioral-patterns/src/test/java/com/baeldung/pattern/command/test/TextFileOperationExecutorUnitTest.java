package com.baeldung.pattern.command.test;

import com.baeldung.pattern.command.command.OpenTextFileOperation;
import com.baeldung.pattern.command.command.SaveTextFileOperation;
import com.baeldung.pattern.command.command.TextFileOperation;
import com.baeldung.pattern.command.invoker.TextFileOperationExecutor;
import com.baeldung.pattern.command.receiver.TextFile;
import java.util.function.Function;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;
import org.junit.BeforeClass;

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
}
