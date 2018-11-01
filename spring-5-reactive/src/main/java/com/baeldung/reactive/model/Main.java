package com.baeldung.reactive.model;

public class Main {


  public static void main(String[] args) {
    Logger consoleLogger = new ConsoleLoggerAdapter();
    Message message = new Message();
    message.setMessage("This is a message");
    MessageCommand consoleMessageCommand =  new MessageCommand(consoleLogger,message);

    consoleMessageCommand.execute();

    Logger fileLogger = new FileLoggerAdapter();
    MessageCommand fileMessageCommand = new MessageCommand(fileLogger,message);
    fileMessageCommand.execute();



  }
}
