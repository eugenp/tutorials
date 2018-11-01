package com.baeldung.reactive.model;

public class MessageCommand {

  private Logger logger;
  private Message message;

  public MessageCommand(Logger logger, Message message){
    this.logger = logger;
    this.message = message;
  }


  public void execute(){
    logger.show(message);
  }
}
