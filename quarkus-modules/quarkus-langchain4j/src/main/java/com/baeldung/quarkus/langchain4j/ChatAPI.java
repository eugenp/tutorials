package com.baeldung.quarkus.langchain4j;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import java.util.UUID;

@Path("/chat")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ChatAPI {

    private ChatBot chatBot;

    public ChatAPI(ChatBot chatBot) {
        this.chatBot = chatBot;
    }

    @POST
    public Answer mesage(@QueryParam("q") String question, @QueryParam("id") UUID chatId) {

        chatId = chatId == null ? UUID.randomUUID() : chatId;

        String message = chatBot.chat(chatId, question);

        return new Answer(message, chatId);
    }


    public record Answer(String message, UUID chatId) {}
}
