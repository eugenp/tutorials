package com.baeldung.pattern.command.client;

import com.baeldung.pattern.command.command.OpenTextFileOperation;
import com.baeldung.pattern.command.command.SaveTextFileOperation;
import com.baeldung.pattern.command.command.TextFileOperation;
import com.baeldung.pattern.command.invoker.TextFileOperationExecutor;
import com.baeldung.pattern.command.receiver.TextFile;

public class TextFileApplication {
    
    public static void main(String[] args) {
        
        TextFileOperation openTextFileOperation = new OpenTextFileOperation(new TextFile("file1.txt"));
        TextFileOperation saveTextFileOperation = new SaveTextFileOperation(new TextFile("file2.txt"));
        TextFileOperationExecutor textFileOperationExecutor = new TextFileOperationExecutor();
        System.out.println(textFileOperationExecutor.executeOperation(openTextFileOperation));
        System.out.println(textFileOperationExecutor.executeOperation(saveTextFileOperation));
    }   
}
