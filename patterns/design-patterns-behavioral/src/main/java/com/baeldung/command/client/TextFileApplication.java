package com.baeldung.command.client;

import com.baeldung.command.command.OpenTextFileOperation;
import com.baeldung.command.command.SaveTextFileOperation;
import com.baeldung.command.command.TextFileOperation;
import com.baeldung.command.invoker.TextFileOperationExecutor;
import com.baeldung.command.receiver.TextFile;

public class TextFileApplication {
    
    public static void main(String[] args) {
        
        TextFileOperation openTextFileOperation = new OpenTextFileOperation(new TextFile("file1.txt"));
        TextFileOperation saveTextFileOperation = new SaveTextFileOperation(new TextFile("file2.txt"));
        TextFileOperationExecutor textFileOperationExecutor = new TextFileOperationExecutor();
        System.out.println(textFileOperationExecutor.executeOperation(openTextFileOperation));
        System.out.println(textFileOperationExecutor.executeOperation(saveTextFileOperation));
    }   
}
