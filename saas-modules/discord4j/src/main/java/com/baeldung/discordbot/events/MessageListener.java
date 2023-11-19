package com.baeldung.discordbot.events;

import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public abstract class MessageListener {

    public Mono<Void> processCommand(Message eventMessage) {
        return Mono.just(eventMessage)
           .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
           .filter(message -> message.getContent().equalsIgnoreCase("!todo"))
           .flatMap(Message::getChannel)
           .flatMap(channel -> channel.createMessage("Things to do today:\n" +
                                                     " - write a bot\n" +
                                                     " - eat lunch\n" +
                                                     " - play a game"))
           .then();
    }
}
